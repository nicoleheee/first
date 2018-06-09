package com.plane.web.conversion;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 日期格式化插件，可用于spring模型转换
 * Created by User on 2016/8/11.
 */
public class DateFormatter implements Formatter<Date>
{


    @Override
    public Date parse(String s, Locale locale) throws ParseException
    {
        final SimpleDateFormat dateFormat = createFormatter(locale);
        return dateFormat.parse(s);
    }

    @Override
    public String print(Date date, Locale locale)
    {
        final SimpleDateFormat dateFormat = createFormatter(locale);
        return dateFormat.format(date);
    }

    private SimpleDateFormat createFormatter(final Locale locale)
    {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",locale);
        dateFormat.setLenient(false);
        return dateFormat;
    }
}
