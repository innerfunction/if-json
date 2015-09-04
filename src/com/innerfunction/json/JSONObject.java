package com.innerfunction.json;

import java.util.EnumSet;

import ns.foundation.NSKeyValueCodingAdditions;
import ns.foundation.NSKeyValueObserving;
import ns.foundation.NSObservable;
import ns.foundation.NSObserver;
import ns.foundation.NSSet;

public class JSONObject extends org.json.simple.JSONObject implements NSKeyValueObserving, NSObservable {

    private static final long serialVersionUID = 1L;

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
    
    public JSONValue.Type getJSONType(String name) {
        return JSONValue.typeOfValue( get( name ) );
    }
    
    public JSONValue.Type resolveJSONType(String path) {
        return JSONValue.typeOfValue( valueForKeyPath( path ) );
    }
    
    public Object resolve(String path) {
        return valueForKeyPath( path );
    }
    
    public Boolean getBoolean(String name) {
        Object value = get( name );
        return value instanceof Boolean ? (Boolean)value : null;
    }
    
    public Boolean resolveBoolean(String path) {
        Object value = valueForKeyPath( path );
        return value instanceof Boolean ? (Boolean)value : null;
    }
    
    public Number getNumber(String name) {
        Object value = get( name );
        return value instanceof Number ? (Number)value : null;
    }
    
    public Number resolveNumber(String path) {
        Object value = valueForKeyPath( path );
        return value instanceof Number ? (Number)value : null;
    }
    
    public String getString(String name) {
        Object value = get( name );
        return value instanceof String ? (String)value : null;
    }
    
    public String resolveString(String path) {
        Object value = valueForKeyPath( path );
        return value instanceof String ? (String)value : null;
    }
    
    public JSONArray getJSONArray(String name) {
        Object value = get( name );
        return value instanceof JSONArray ? (JSONArray)value : null;
    }
    
    public JSONArray resolveJSONArray(String path) {
        Object value = valueForKeyPath( path );
        return value instanceof JSONArray ? (JSONArray)value : null;
    }
    
    public JSONObject getJSONObject(String name) {
        Object value = get( name );
        return value instanceof JSONObject ? (JSONObject)value : null;
    }
    
    public JSONObject resolveJSONObject(String path) {
        Object value = valueForKeyPath( path );
        return value instanceof JSONObject ? (JSONObject)value : null;
    }

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

}
