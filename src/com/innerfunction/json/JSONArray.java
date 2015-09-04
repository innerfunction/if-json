package com.innerfunction.json;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import ns.foundation.NSKeyValueCodingAdditions;
import ns.foundation.NSKeyValueObserving;
import ns.foundation.NSObservable;
import ns.foundation.NSObserver;
import ns.foundation.NSSet;

public class JSONArray extends org.json.simple.JSONArray implements NSKeyValueObserving, NSObservable {

    private static final long serialVersionUID = 1L;
    
    @Override
    @SuppressWarnings("unchecked")
    public Object set(int idx, Object value) {
        String key = Integer.toString( idx );
        willChangeValueForKey( key );
        Object result = super.set( idx, value );
        didChangeValueForKey( key );
        return result;
    }
    
    public Object get(String name) {
        try {
            int idx = Integer.parseInt( name );
            return get( idx );
        }
        catch(NumberFormatException e) {
            return null;
        }
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
                value = this;
            }
            else if("allKeys".equals( key ) ) {
                Set<Integer> keySet = new HashSet<Integer>();
                for( int i = 0; i < size(); i++ ) {
                    keySet.add( i );
                }
                value = keySet;
            }
            else if("count".equals( key ) ) {
                value = size();
            }
        }
        return value;
    }
    
    @Override
    public void takeValueForKey(Object value, String key) {
        try {
            int idx = Integer.parseInt( key );
            set( idx, value );
        }
        catch(NumberFormatException e) {}
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
