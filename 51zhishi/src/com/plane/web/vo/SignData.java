package com.plane.web.vo;

/**
 * Created by User on 2016/9/22.
 */
public class SignData
{
    private String modulus;

    private String publicExponent;

    private  String privateExponent;

    public String getModulus()
    {
        return modulus;
    }

    public void setModulus(String modulus)
    {
        this.modulus = modulus;
    }

    public String getPublicExponent()
    {
        return publicExponent;
    }

    public void setPublicExponent(String publicExponent)
    {
        this.publicExponent = publicExponent;
    }

    public String getPrivateExponent()
    {
        return privateExponent;
    }

    public void setPrivateExponent(String privateExponent)
    {
        this.privateExponent = privateExponent;
    }
}
