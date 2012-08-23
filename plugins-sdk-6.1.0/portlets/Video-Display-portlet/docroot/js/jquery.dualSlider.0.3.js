/**
* jQuery.fn.dualSlider - Dual sliders, why not?
* Date: June 2010
*
* @author Rob Phillips (Front End Developer - Hugo & Cat - http://www.hugoandcat.com)
* @version 0.3
* @web http://www.hugoandcat.com/DualSlider/index.html
*
* Requirements:
* jquery.1.3.2.js - http://jquery.com/
* jquery.easing.1.3.js - http://gsgd.co.uk/sandbox/jquery/easing/
* jquery.timers-1.2.js - http://plugins.jquery.com/project/timers
*
* 0.2 - Tested and fixed for IE6+, auto loops, manual pause/play controls
*     - Disabled buttons until animation finishes - Thanks for the bug John.
* 0.3 - Now with a seamless loop, instead of that nasty rewind...was 'too much' apparently
*
**/

var myobj="";
var fRef="";
jQuery( document).ready(function() {

    jQuery.fn.dualSlider = function(options) {

        // default configuration properties
        var defaults = {
            auto: true,
            autoDelay: 10000,
            easingCarousel: 'swing',
            easingDetails: 'easeOutBack',
            durationCarousel: 1000,
            durationDetails: 600
        };

        var options = jQuery.extend(defaults, options);

        this.each(function() {

            var obj = jQuery(this);
			myobj = obj;
            var carousel;
            var carouselTotal = jQuery(".backgrounds", obj).children().length;
            var carouselPosition = 1;
            var carouselLinkIndex = 1;
            var carouselLinks = "";
            var carouselwidth =jQuery(obj).width();/* jQuery(obj).width(); KALEEM*/

			//alert( ' carouselwidth :'+ carouselwidth );
			
            var detailWidth = jQuery(".panel .details_wrapper", obj).width();
			//alert( ' detailWidth :'+detailWidth );
            var locked = false;
			if(options.auto == true)
			{
				//Creat duplicates for seamless looping
				jQuery(".backgrounds", obj).prepend(jQuery(".backgrounds .item:last-child", obj).clone().css("margin-left", "-" + carouselwidth + "px"));
				jQuery(".backgrounds", obj).append(jQuery(".backgrounds .item:nth-child(2)", obj).clone());

				jQuery(".details", obj).prepend(jQuery(".details .detail:last-child", obj).clone().css("margin-left", "-" + detailWidth + "px"));
				jQuery(".details", obj).append(jQuery(".details .detail:nth-child(2)", obj).clone());
			}
			else{
				jQuery(".previous", obj).hide();
				jQuery(".play, .pause", obj).hide();
			}


            //Set main background width
            jQuery(".backgrounds", obj).css("width", ((carouselTotal+1) * carouselwidth) + 100 + "px");
            

            //Set main detail width
            jQuery(".details_wrapper .details", obj).css("width", detailWidth * carouselwidth + "px");

            for (i = 1; i <= carouselTotal; i++) {
                (i == 1) ? carouselLinks += "<a rel=\"" + carouselLinkIndex + "\" title=\"Go to page " + carouselLinkIndex + " \" class=\"link" + carouselLinkIndex + " selected\" href=\"javascript:void(0)\">" + carouselLinkIndex + "</a>" : carouselLinks += "<a rel=\"" + carouselLinkIndex + "\"  title=\"Go to page " + carouselLinkIndex + " \" class=\"link" + carouselLinkIndex + "\" href=\"javascript:void(0)\" >" + carouselLinkIndex + "</a>";
                carouselLinkIndex++;
            }
            jQuery("#numbers", obj).html(carouselLinks);

            //Bind carousel controls
            jQuery(".next", obj).click(function() {
                carouselPage(parseInt(carouselPosition + 1), false);
                lock();
            });
            
            jQuery(".previous", obj).click(function() {
                carouselPage(parseInt(carouselPosition - 1), false);
                lock();
            });

            jQuery("#numbers a", obj).click(function() {
                carouselPage(jQuery(this).attr("rel"), false);
                lock();
            });

            jQuery(".pause", obj).click(function() {
                autoPause();
            });
            jQuery(".play", obj).click(function() {
                autoPlay();
            });

            function lock() {
                locked = true;
            }

            function unLock() {
                locked = false;
            }


            function checkPreviousNext() {
                jQuery("#numbers a", obj).removeClass("selected");
                jQuery("#numbers .link" + carouselPosition, obj).addClass("selected");
                
				if(options.auto == false)
				{
					(carouselPosition == carouselTotal) ? jQuery(".next", obj).hide() : jQuery(".next", obj).show();
					(carouselPosition < 2) ? jQuery(".previous", obj).hide() : jQuery(".previous", obj).show();
				}
            }
			
			function adjust() {

                if (carouselPosition < 1) {
                    //alert("trickery required");
                    jQuery(".backgrounds", obj).css("margin-left", (-1 * ((carouselTotal - 1) * carouselwidth)));
                    jQuery(".details", obj).css("margin-left", (-1 * ((carouselTotal - 1) * detailWidth)));
                    carouselPosition = carouselTotal;

                }
                if (carouselPosition > carouselTotal) {
                    //alert("more trickery required");
                    jQuery(".backgrounds", obj).css("margin-left", 0);
                    jQuery(".details", obj).css("margin-left", 0);
                    carouselPosition = 1;
                }

            }

            function carouselPage(x, y) {

                if (locked != true) {

                    //console.log("New page: " + x);
                    carouselPosition = parseFloat(x);
                    //Cancel timer if manual click
                    if (y == false) autoPause();

                    var newPage = (x * carouselwidth) - carouselwidth;
                    var newPageDetail = (x * detailWidth) - detailWidth;

                    if (newPage != 0) {
                        newPage = newPage * -1;
                        newPageDetail = newPageDetail * -1;
                    }

                    jQuery(".backgrounds", obj).animate({
                        marginLeft: newPage
                    }, {
                        "duration": options.durationCarousel, "easing": options.easingCarousel,

                        complete: function() {

                            //Now animate the details
                            jQuery(".details", obj).animate({
                                marginLeft: newPageDetail
                            }, {
                                "duration": options.durationDetails, "easing": options.easingDetails

                            });
                            adjust();
							checkPreviousNext();
                            unLock();
                        }
                    });
                }
                

            }

            function autoPause() {
                jQuery(".pause", obj).hide();
                jQuery(".play", obj).show();
                jQuery("body").stopTime("autoScroll");
            }

          function autoPlay() {
                jQuery(".pause", obj).show();
                jQuery(".play", obj).hide();
                   jQuery("body").everyTime(options.autoDelay, "autoScroll", function() {
                    carouselPage(carouselPosition + 1, true);
                    lock();
                });
            }

            if (options.auto == true) {
                autoPlay();
            }

        });

    };
   
});



