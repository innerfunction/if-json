package com.innerfunction.json;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ns.foundation.NSKeyValueCodingAdditions;
import ns.foundation.NSKeyValueObserving;
import ns.foundation.NSObserver;
import ns.foundation.NSSet;

@SuppressWarnings("rawtypes")
public class JSONArray extends ArrayList implements JSONAware, JSONStreamAware, NSKeyValueObserving.KeyValueObservingProxyCacheAware {
    
    private static final long serialVersionUID = 3957988303675231981L;

    private Object nskvProxyCacheKey = new Object();

    /**
     * Constructs an empty JSONArray.
     */
    public JSONArray(){
        super();
    }
    
    /**
     * Constructs a JSONArray containing the elements of the specified
     * collection, in the order they are returned by the collection's iterator.
     * 
     * @param c the collection whose elements are to be placed into this JSONArray
     */
    @SuppressWarnings("unchecked")
    public JSONArray(Collection c){
        super(c);
    }
    
    public Object getNSKVProxyCacheKey() {
        return nskvProxyCacheKey;
    }

    /**
     * Encode a list into JSON text and write it to out. 
     * If this list is also a JSONStreamAware or a JSONAware, JSONStreamAware and JSONAware specific behaviours will be ignored at this top level.
     * 
     * @see org.json.simple.JSONValue#writeJSONString(Object, Writer)
     * 
     * @param collection
     * @param out
     */
    public static void writeJSONString(Collection collection, Writer out) throws IOException{
        if(collection == null){
            out.write("null");
            return;
        }
        
        boolean first = true;
        Iterator iter=collection.iterator();
        
        out.write('[');
        while(iter.hasNext()){
            if(first)
                first = false;
            else
                out.write(',');
            
            Object value=iter.next();
            if(value == null){
                out.write("null");
                continue;
            }
            
            JSONValue.writeJSONString(value, out);
        }
        out.write(']');
    }
    
    public void writeJSONString(Writer out) throws IOException{
        writeJSONString(this, out);
    }
    
    
    public String toJSONString(){
        return toJSONString(this);
    }

    /**
     * Returns a string representation of this array. This is equivalent to
     * calling {@link JSONArray#toJSONString()}.
     */
    public String toString() {
        return toJSONString();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public boolean add(Object item) {
        String key = Integer.toString( size() );
        willChangeValueForKey( key );
        boolean result = super.add( item );
        didChangeValueForKey( key );
        return result;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void add(int idx, Object item) {
        String key = Integer.toString( idx );
        willChangeValueForKey( key );
        super.add( idx, item );
        didChangeValueForKey( key );
    }

    @Override
    public boolean addAll(Collection c) {
        return addAll( size(), c );
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public boolean addAll(int idx, Collection c) {
        List<String> keys = new ArrayList<String>();
        for( int i = 0; i < c.size(); i++ ) {
            String key = Integer.toString( idx + i );
            keys.add( key );
            willChangeValueForKey( key );
        }
        boolean result = super.addAll( idx, c );
        for( String key : keys ) {
            didChangeValueForKey( key );
        }
        return result;
    }
    
    @Override
    public void clear() {
        List<String> keys = new ArrayList<String>();
        for( int i = 0; i < size(); i++ ) {
            String key = Integer.toString( i );
            keys.add( key );
            willChangeValueForKey( key );
        }
        super.clear();
        for( String key : keys ) {
            didChangeValueForKey( key );
        }
    }
    
    @Override
    public Object remove(int idx) {
        String key = Integer.toString( idx );
        willChangeValueForKey( key );
        Object result = remove( idx );
        didChangeValueForKey( key );
        return result;
    }
    
    @Override
    public boolean remove(Object obj) {
        int idx = 0;
        for(Object item : this) {
            if( (item == null && obj == null) || item.equals( obj ) ) {
                remove( idx );
                return true;
            }
            idx++;
        }
        return false;
    }
    
    @Override
    public boolean removeAll(Collection c) {
        boolean result = false;
        for(Object obj : c) {
            result |= remove( obj );
        }
        return result;
    }
    
    @Override
    public boolean retainAll(Collection c) {
        boolean result = false;
        for(Object obj : this) {
            if( !c.contains( obj ) ) {
                result |= remove( obj );
            }
        }
        return result;
    }
    
    @Override
    public void set(String name, Object value) {
        takeValueForKey( value, name );
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object set(int idx, Object value) {
        String key = Integer.toString( idx );
        willChangeValueForKey( key );
        Object result = super.set( idx, value );
        didChangeValueForKey( key );
        return result;
    }
    
    @Override
    public void setPath(String path, Object value) {
        takeValueForKeyPath( value, path );
    }
    
    @Override
    public void removePath(String path) {
        takeValueForKeyPath( null, path );
    }
    
    public Object get(String name) {
        try {
            int idx = Integer.parseInt( name );
            if( idx < size() ) {
                return get( idx );
            }
            return null;
        }
        catch(NumberFormatException e) {
            return null;
        }
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
    
    public Boolean getBoolean(int idx) {
        Object value = get( idx );
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
    
    public Number getNumber(int idx) {
        Object value = get( idx );
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

    public String getString(int idx) {
        Object value = get( idx );
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

    public JSONArray getJSONArray(int idx) {
        Object value = get( idx );
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

    public JSONObject getJSONObject(int idx) {
        Object value = get( idx );
        return value instanceof JSONObject ? (JSONObject)value : null;
    }

    @Override
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

    /**
     * Convert a list to JSON text. The result is a JSON array. 
     * If this list is also a JSONAware, JSONAware specific behaviours will be omitted at this top level.
     * 
     * @see org.json.simple.JSONValue#toJSONString(Object)
     * 
     * @param collection
     * @return JSON text, or "null" if list is null.
     */
    public static String toJSONString(Collection collection){
        final StringWriter writer = new StringWriter();
        
        try {
            writeJSONString(collection, writer);
            return writer.toString();
        } catch(IOException e){
            // This should never happen for a StringWriter
            throw new RuntimeException(e);
        }
    }

    public static void writeJSONString(byte[] array, Writer out) throws IOException{
        if(array == null){
            out.write("null");
        } else if(array.length == 0) {
            out.write("[]");
        } else {
            out.write("[");
            out.write(String.valueOf(array[0]));
            
            for(int i = 1; i < array.length; i++){
                out.write(",");
                out.write(String.valueOf(array[i]));
            }
            
            out.write("]");
        }
    }
    
    public static String toJSONString(byte[] array){
        final StringWriter writer = new StringWriter();
        
        try {
            writeJSONString(array, writer);
            return writer.toString();
        } catch(IOException e){
            // This should never happen for a StringWriter
            throw new RuntimeException(e);
        }
    }
    
    public static void writeJSONString(short[] array, Writer out) throws IOException{
        if(array == null){
            out.write("null");
        } else if(array.length == 0) {
            out.write("[]");
        } else {
            out.write("[");
            out.write(String.valueOf(array[0]));
            
            for(int i = 1; i < array.length; i++){
                out.write(",");
                out.write(String.valueOf(array[i]));
            }
            
            out.write("]");
        }
    }
    
    public static String toJSONString(short[] array){
        final StringWriter writer = new StringWriter();
        
        try {
            writeJSONString(array, writer);
            return writer.toString();
        } catch(IOException e){
            // This should never happen for a StringWriter
            throw new RuntimeException(e);
        }
    }
    
    public static void writeJSONString(int[] array, Writer out) throws IOException{
        if(array == null){
            out.write("null");
        } else if(array.length == 0) {
            out.write("[]");
        } else {
            out.write("[");
            out.write(String.valueOf(array[0]));
            
            for(int i = 1; i < array.length; i++){
                out.write(",");
                out.write(String.valueOf(array[i]));
            }
            
            out.write("]");
        }
    }
    
    public static String toJSONString(int[] array){
        final StringWriter writer = new StringWriter();
        
        try {
            writeJSONString(array, writer);
            return writer.toString();
        } catch(IOException e){
            // This should never happen for a StringWriter
            throw new RuntimeException(e);
        }
    }
    
    public static void writeJSONString(long[] array, Writer out) throws IOException{
        if(array == null){
            out.write("null");
        } else if(array.length == 0) {
            out.write("[]");
        } else {
            out.write("[");
            out.write(String.valueOf(array[0]));
            
            for(int i = 1; i < array.length; i++){
                out.write(",");
                out.write(String.valueOf(array[i]));
            }
            
            out.write("]");
        }
    }
    
    public static String toJSONString(long[] array){
        final StringWriter writer = new StringWriter();
        
        try {
            writeJSONString(array, writer);
            return writer.toString();
        } catch(IOException e){
            // This should never happen for a StringWriter
            throw new RuntimeException(e);
        }
    }
    
    public static void writeJSONString(float[] array, Writer out) throws IOException{
        if(array == null){
            out.write("null");
        } else if(array.length == 0) {
            out.write("[]");
        } else {
            out.write("[");
            out.write(String.valueOf(array[0]));
            
            for(int i = 1; i < array.length; i++){
                out.write(",");
                out.write(String.valueOf(array[i]));
            }
            
            out.write("]");
        }
    }
    
    public static String toJSONString(float[] array){
        final StringWriter writer = new StringWriter();
        
        try {
            writeJSONString(array, writer);
            return writer.toString();
        } catch(IOException e){
            // This should never happen for a StringWriter
            throw new RuntimeException(e);
        }
    }
    
    public static void writeJSONString(double[] array, Writer out) throws IOException{
        if(array == null){
            out.write("null");
        } else if(array.length == 0) {
            out.write("[]");
        } else {
            out.write("[");
            out.write(String.valueOf(array[0]));
            
            for(int i = 1; i < array.length; i++){
                out.write(",");
                out.write(String.valueOf(array[i]));
            }
            
            out.write("]");
        }
    }
    
    public static String toJSONString(double[] array){
        final StringWriter writer = new StringWriter();
        
        try {
            writeJSONString(array, writer);
            return writer.toString();
        } catch(IOException e){
            // This should never happen for a StringWriter
            throw new RuntimeException(e);
        }
    }
    
    public static void writeJSONString(boolean[] array, Writer out) throws IOException{
        if(array == null){
            out.write("null");
        } else if(array.length == 0) {
            out.write("[]");
        } else {
            out.write("[");
            out.write(String.valueOf(array[0]));
            
            for(int i = 1; i < array.length; i++){
                out.write(",");
                out.write(String.valueOf(array[i]));
            }
            
            out.write("]");
        }
    }
    
    public static String toJSONString(boolean[] array){
        final StringWriter writer = new StringWriter();
        
        try {
            writeJSONString(array, writer);
            return writer.toString();
        } catch(IOException e){
            // This should never happen for a StringWriter
            throw new RuntimeException(e);
        }
    }
    
    public static void writeJSONString(char[] array, Writer out) throws IOException{
        if(array == null){
            out.write("null");
        } else if(array.length == 0) {
            out.write("[]");
        } else {
            out.write("[\"");
            out.write(String.valueOf(array[0]));
            
            for(int i = 1; i < array.length; i++){
                out.write("\",\"");
                out.write(String.valueOf(array[i]));
            }
            
            out.write("\"]");
        }
    }
    
    public static String toJSONString(char[] array){
        final StringWriter writer = new StringWriter();
        
        try {
            writeJSONString(array, writer);
            return writer.toString();
        } catch(IOException e){
            // This should never happen for a StringWriter
            throw new RuntimeException(e);
        }
    }
    
    public static void writeJSONString(Object[] array, Writer out) throws IOException{
        if(array == null){
            out.write("null");
        } else if(array.length == 0) {
            out.write("[]");
        } else {
            out.write("[");
            JSONValue.writeJSONString(array[0], out);
            
            for(int i = 1; i < array.length; i++){
                out.write(",");
                JSONValue.writeJSONString(array[i], out);
            }
            
            out.write("]");
        }
    }
    
    public static String toJSONString(Object[] array){
        final StringWriter writer = new StringWriter();
        
        try {
            writeJSONString(array, writer);
            return writer.toString();
        } catch(IOException e){
            // This should never happen for a StringWriter
            throw new RuntimeException(e);
        }
    }
    
}
