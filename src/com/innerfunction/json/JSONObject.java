package com.innerfunction.json;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.EnumSet;
import java.util.Set;

import ns.foundation.NSKeyValueCodingAdditions;
import ns.foundation.NSKeyValueObserving;
import ns.foundation.NSObservable;
import ns.foundation.NSObserver;
import ns.foundation.NSSet;

@SuppressWarnings("rawtypes")
public class JSONObject extends HashMap implements Map, JSONAware, JSONStreamAware, NSKeyValueObserving, NSObservable {

    private static final long serialVersionUID = -503443796854799292L;
    
    public JSONObject() {
        super();
    }
    
    /**
     * Allows creation of a JSONObject from a Map. After that, both the
     * generated JSONObject and the Map can be modified independently.
     * 
     * @param map
     */
    @SuppressWarnings("unchecked")
    public JSONObject(Map map) {
        super(map);
    }

    public void writeJSONString(Writer out) throws IOException{
        writeJSONString(this, out);
    }
    
    public String toJSONString(){
        return toJSONString(this);
    }
    
    public String toString(){
        return toJSONString();
    }
    
    @Override
    public void clear() {
        Set keys = keySet();
        for( Object key : keys ) {
            willChangeValueForKey( key.toString() );
        }
        super.clear();
        for( Object key : keys ) {
            didChangeValueForKey( key.toString() );
        }
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public Object put(Object key, Object value) {
        Object result = null;
        if( key != null ) {
            String skey = key.toString();
            willChangeValueForKey( skey );
            result = super.put( key, value );
            didChangeValueForKey( skey );
        }
        return result;
    }
    
    @Override
    public void putAll(Map values) {
        for (Object key : values.keySet() ) {
            put( key, values.get( key ) );
        }
    }
    
    @Override
    public Object remove(Object key) {
        Object result = null;
        if( key != null ) {
            String skey = key.toString();
            willChangeValueForKey( skey );
            result = super.remove( key );
            didChangeValueForKey( skey );
        }
        return result;
    }
    
    @Override
    public JSONValue.Type getJSONType(String name) {
        return JSONValue.typeOfValue( get( name ) );
    }
    
    @Override
    public JSONValue.Type resolveJSONType(String path) {
        return JSONValue.typeOfValue( valueForKeyPath( path ) );
    }
    
    @Override
    public Object resolve(String path) {
        return valueForKeyPath( path );
    }
    
    @Override
    public Boolean getBoolean(String name) {
        Object value = get( name );
        return value instanceof Boolean ? (Boolean)value : null;
    }
    
    @Override
    public Boolean resolveBoolean(String path) {
        Object value = valueForKeyPath( path );
        return value instanceof Boolean ? (Boolean)value : null;
    }
    
    @Override
    public Number getNumber(String name) {
        Object value = get( name );
        return value instanceof Number ? (Number)value : null;
    }
    
    @Override
    public Number resolveNumber(String path) {
        Object value = valueForKeyPath( path );
        return value instanceof Number ? (Number)value : null;
    }
    
    @Override
    public String getString(String name) {
        Object value = get( name );
        return value instanceof String ? (String)value : null;
    }
    
    @Override
    public String resolveString(String path) {
        Object value = valueForKeyPath( path );
        return value instanceof String ? (String)value : null;
    }
    
    @Override
    public JSONArray getJSONArray(String name) {
        Object value = get( name );
        return value instanceof JSONArray ? (JSONArray)value : null;
    }
    
    @Override
    public JSONArray resolveJSONArray(String path) {
        Object value = valueForKeyPath( path );
        return value instanceof JSONArray ? (JSONArray)value : null;
    }
    
    @Override
    public JSONObject getJSONObject(String name) {
        Object value = get( name );
        return value instanceof JSONObject ? (JSONObject)value : null;
    }
    
    @Override
    public JSONObject resolveJSONObject(String path) {
        Object value = valueForKeyPath( path );
        return value instanceof JSONObject ? (JSONObject)value : null;
    }

    /*
    @Override
    public Object removeValueAtPath(String path) {
    }
    */
    
    @Override
    public void takeValueForKeyPath(Object value, String keyPath) {
        NSKeyValueCodingAdditions.DefaultImplementation.takeValueForKeyPath( this, value, keyPath );
    }

    @Override
    public Object valueForKeyPath(String keyPath) {
        Object flattenedKeyPresent = get( keyPath );
        if( flattenedKeyPresent != null ) {
            return flattenedKeyPresent;
        }
        return NSKeyValueCodingAdditions.DefaultImplementation.valueForKeyPath( this, keyPath );
    }

    @Override
    public Object valueForKey(String key) {
        Object value = get( key );
        if( value == null && key != null ) {
            if("allValues".equals( key ) ) {
                value = values();
            }
            else if("allKeys".equals( key ) ) {
                value = keySet();
            }
            else if("count".equals( key ) ) {
                value = size();
            }
        }
        return value;
    }

    @Override
    public void takeValueForKey(Object value, String key) {
        put( key, value );
    }

    @Override
    public boolean automaticallyNotifiesObserversForKey(String key) {
        return NSKeyValueObserving.DefaultImplementation.automaticallyNotifiesObserversForKey( this, key );
    }

    @Override
    public void addObserverForKeyPath(NSObserver observer, String keyPath, EnumSet<Options> options, Object context) {
        NSKeyValueObserving.DefaultImplementation.addObserverForKeyPath( this, observer, keyPath, options, context );
    }

    @Override
    public void didChangeValuesAtIndexForKey(EnumSet<Changes> change, NSSet<Integer> indexes, String key) {
        NSKeyValueObserving.DefaultImplementation.didChangeValuesAtIndexForKey( this, change, indexes, key );
    }

    @Override
    public void didChangeValueForKey(String key) {
        NSKeyValueObserving.DefaultImplementation.didChangeValueForKey( this, key );
    }

    @Override
    public void removeObserverForKeyPath(NSObserver observer, String keyPath) {
        NSKeyValueObserving.DefaultImplementation.removeObserverForKeyPath( this, observer, keyPath );
    }

    @Override
    public void willChangeValuesAtIndexForKey(EnumSet<Changes> change, NSSet<Integer> indexes, String key) {
        NSKeyValueObserving.DefaultImplementation.willChangeValuesAtIndexForKey( this, change, indexes, key );
    }

    @Override
    public void willChangeValueForKey(String key) {
        NSKeyValueObserving.DefaultImplementation.willChangeValueForKey( this, key );
    }

    @Override
    public NSSet<String> keyPathsForValuesAffectingValueForKey(String key) {
        return NSKeyValueObserving.DefaultImplementation.keyPathsForValuesAffectingValueForKey( this, key );
    }

    /**
     * Encode a map into JSON text and write it to out.
     * If this map is also a JSONAware or JSONStreamAware, JSONAware or JSONStreamAware specific behaviours will be ignored at this top level.
     * 
     * @see org.json.simple.JSONValue#writeJSONString(Object, Writer)
     * 
     * @param map
     * @param out
     */
    public static void writeJSONString(Map map, Writer out) throws IOException {
        if(map == null){
            out.write("null");
            return;
        }
        
        boolean first = true;
        Iterator iter=map.entrySet().iterator();
        
        out.write('{');
        while(iter.hasNext()){
            if(first)
                first = false;
            else
                out.write(',');
            Map.Entry entry=(Map.Entry)iter.next();
            out.write('\"');
            out.write(escape(String.valueOf(entry.getKey())));
            out.write('\"');
            out.write(':');
            JSONValue.writeJSONString(entry.getValue(), out);
        }
        out.write('}');
    }

    /**
     * Convert a map to JSON text. The result is a JSON object. 
     * If this map is also a JSONAware, JSONAware specific behaviours will be omitted at this top level.
     * 
     * @see org.json.simple.JSONValue#toJSONString(Object)
     * 
     * @param map
     * @return JSON text, or "null" if map is null.
     */
    public static String toJSONString(Map map){
        final StringWriter writer = new StringWriter();
        
        try {
            writeJSONString(map, writer);
            return writer.toString();
        } catch (IOException e) {
            // This should never happen with a StringWriter
            throw new RuntimeException(e);
        }
    }
    
    public static String toString(String key,Object value){
        StringBuffer sb = new StringBuffer();
        sb.append('\"');
        if(key == null)
            sb.append("null");
        else
            JSONValue.escape(key, sb);
        sb.append('\"').append(':');
        
        sb.append(JSONValue.toJSONString(value));
        
        return sb.toString();
    }
    
    /**
     * Escape quotes, \, /, \r, \n, \b, \f, \t and other control characters (U+0000 through U+001F).
     * It's the same as JSONValue.escape() only for compatibility here.
     * 
     * @see org.json.simple.JSONValue#escape(String)
     * 
     * @param s
     * @return
     */
    public static String escape(String s){
        return JSONValue.escape(s);
    }

}
