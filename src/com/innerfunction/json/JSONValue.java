package com.innerfunction.json;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.json.simple.parser.ParseException;

import com.innerfunction.json.parser.JSONParser;

public class JSONValue extends org.json.simple.JSONValue {

    public enum Type { Null, Boolean, Number, String, Object, Array, Other };
    
    public static Type typeOfValue(Object value) {
        if( value == null ) {
            return Type.Null;
        }
        if( value instanceof Boolean ) {
            return Type.Boolean;
        }
        if( value instanceof Number ) {
            return Type.Number;
        }
        if( value instanceof String ) {
            return Type.String;
        }
        if( value instanceof JSONArray ) {
            return Type.Array;
        }
        if( value instanceof JSONObject ) {
            return Type.Object;
        }
        return Type.Other;
    }
    /**
     * Parse JSON text into java object from the input source. 
     * Please use parseWithException() if you don't want to ignore the exception.
     * 
     * @see org.json.simple.parser.JSONParser#parse(Reader)
     * @see #parseWithException(Reader)
     * 
     * @param in
     * @return Instance of the following:
     *  org.json.simple.JSONObject,
     *  org.json.simple.JSONArray,
     *  java.lang.String,
     *  java.lang.Number,
     *  java.lang.Boolean,
     *  null
     * 
     * @deprecated this method may throw an {@code Error} instead of returning
     * {@code null}; please use {@link JSONValue#parseWithException(Reader)}
     * instead
     */
    public static Object parse(Reader in){
        try{
            JSONParser parser=new JSONParser();
            return parser.parse(in);
        }
        catch(Exception e){
            return null;
        }
    }
    
    /**
     * Parse JSON text into java object from the given string. 
     * Please use parseWithException() if you don't want to ignore the exception.
     * 
     * @see org.json.simple.parser.JSONParser#parse(Reader)
     * @see #parseWithException(Reader)
     * 
     * @param s
     * @return Instance of the following:
     *  org.json.simple.JSONObject,
     *  org.json.simple.JSONArray,
     *  java.lang.String,
     *  java.lang.Number,
     *  java.lang.Boolean,
     *  null
     * 
     * @deprecated this method may throw an {@code Error} instead of returning
     * {@code null}; please use {@link JSONValue#parseWithException(String)}
     * instead
     */
    public static Object parse(String s){
        StringReader in=new StringReader(s);
        return parse(in);
    }
    
    /**
     * Parse JSON text into java object from the input source.
     * 
     * @see org.json.simple.parser.JSONParser
     * 
     * @param in
     * @return Instance of the following:
     *  org.json.simple.JSONObject,
     *  org.json.simple.JSONArray,
     *  java.lang.String,
     *  java.lang.Number,
     *  java.lang.Boolean,
     *  null
     * 
     * @throws IOException
     * @throws ParseException
     */
    public static Object parseWithException(Reader in) throws IOException, ParseException{
        JSONParser parser=new JSONParser();
        return parser.parse(in);
    }
    
    public static Object parseWithException(String s) throws ParseException{
        JSONParser parser=new JSONParser();
        return parser.parse(s);
    }

}
