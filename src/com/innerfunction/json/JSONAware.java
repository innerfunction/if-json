package com.innerfunction.json;

/**
 * Beans that support customized output of JSON text shall implement this interface.  
 * @author FangYidong<fangyidong@yahoo.com.cn>
 */
public interface JSONAware {
    
    /**
     * Get the type of a named property.
     * @param name A property name.
     * @return The property type.
     */
    public JSONValue.Type getJSONType(String name);

    /**
     * Resolve the type of a value reference by a property path.
     * @param path A dotted property path.
     * @return The referenced property type.
     */
    public JSONValue.Type resolveJSONType(String path);

    /**
     * Get a named property.
     * @param name A property name.
     * @return The property's value, or null if the property is null.
     */
//    public Object get(String name);

    /**
     * Get a named boolean property.
     * @param name A property name.
     * @return The property's boolean value, or null if the property is null or is not a boolean.
     */
    public Boolean getBoolean(String name);
    
    /**
     * Get a named number property.
     * @param name A property name.
     * @return The property's number value, or null if the property is null or is not a number.
     */
    public Number getNumber(String name);
    
    /**
     * Get a named string property.
     * @param name A property name.
     * @return The property's string value, or null if the property is null or is not a string.
     */
    public String getString(String name);
    
    /**
     * Get a named object property.
     * @param name A property name.
     * @return The property's object value, or null if the property is null or is not a JSON object.
     */
    public JSONObject getJSONObject(String name);
    
    /**
     * Get a named array property.
     * @param name A property name.
     * @return The property's array value, or null if the property is null or is not a JSON array.
     */
    public JSONArray getJSONArray(String name);
    
    /**
     * Resolve a value referenced by a property path.
     * @param name A property name.
     * @return The property's value.
     */
    public Object resolve(String path);
    
    /**
     * Resolve a boolean value referenced by a property path.
     * @param path A dotted path reference.
     * @return The referenced property's boolean value, or null if the property is null or is not a boolean.
     */
    public Boolean resolveBoolean(String path);
    
    /**
     * Resolve a number value referenced by a property path.
     * @param path A dotted path reference.
     * @return The referenced property's number value, or null if the property is null or is not a number.
     */
    public Number resolveNumber(String path);
    
    /**
     * Resolve a string value referenced by a property path.
     * @param path A dotted path reference.
     * @return The referenced property's string value, or null if the property is null or is not a string.
     */
    public String resolveString(String path);
    
    /**
     * Resolve a object value referenced by a property path.
     * @param path A dotted path reference.
     * @return The referenced property's object value, or null if the property is null or is not a JSON object.
     */
    public JSONObject resolveJSONObject(String path);
    
    /**
     * Resolve a array value referenced by a property path.
     * @param path A dotted path reference.
     * @return The referenced property's array value, or null if the property is null or is not a JSON array.
     */
    public JSONArray resolveJSONArray(String path);
    
    /**
     * Remove the value referenced by a property path.
     * @param path A dotted path reference.
     * @return The removed value.
     */
    //public Object removeValueAtPath(String path);
    
    /**
     * @return JSON text
     */
    public String toJSONString();
}