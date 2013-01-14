package com.rcs.portlet.slider.model;


public class Slide
{

    long id;
    String title;
    String link;
    String imageUrl;
    String desc;
    String timeMillis;
    int order;

    public Slide()
    {
        id = 0L;
        title = null;
        link = null;
        imageUrl = null;
        desc = null;
        timeMillis = null;
        order = 0;
    }

    public Slide(String id, String title, String link, String imageUrl, String desc, String timeMillis, int order)
    {
        this.id = 0L;
        this.title = null;
        this.link = null;
        this.imageUrl = null;
        this.desc = null;
        this.timeMillis = null;
        this.order = 0;
        id = id.replaceAll("slides_", "");
        this.id = Long.parseLong(id);
        this.title = title;
        this.link = link;
        this.imageUrl = imageUrl;
        this.timeMillis = timeMillis;
        this.desc = desc;
        this.order = order;
    }

    public String getTimeMillis()
    {
        return timeMillis;
    }

    public void setTimeMillis(String timeMillis)
    {
        this.timeMillis = timeMillis;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public int getOrder()
    {
        return order;
    }

    public void setOrder(int order)
    {
        this.order = order;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getLink()
    {
        return link;
    }

    public void setLink(String link)
    {
        this.link = link;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }
}
