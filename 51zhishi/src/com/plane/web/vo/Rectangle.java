package com.plane.web.vo;

/**
 * Created by suzhengkun on 2016/11/10.
 */
public class Rectangle
{
    Integer x;
    Integer y;
    Integer width;
    Integer height;

    public Rectangle()
    {
    }
    public Rectangle(Integer width, Integer height)
    {
        this.width = width;
        this.height = height;
    }

    public Rectangle(Integer x, Integer y, Integer width, Integer height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Integer getX()
    {
        return x;
    }

    public void setX(Integer x)
    {
        this.x = x;
    }

    public Integer getY()
    {
        return y;
    }

    public void setY(Integer y)
    {
        this.y = y;
    }

    public Integer getWidth()
    {
        return width;
    }

    public void setWidth(Integer width)
    {
        this.width = width;
    }

    public Integer getHeight()
    {
        return height;
    }

    public void setHeight(Integer height)
    {
        this.height = height;
    }
}
