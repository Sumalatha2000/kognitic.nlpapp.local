package com.kognitic.nlpapp.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ClassUtils;

public class ReflectUtil {

	public static boolean isPrimitive(Field f) {
		return f.getType().isPrimitive();
	}

	public static boolean isString(Field f) {
		if (f.getType() == String.class)
			return true;
		return false;
	}
	
	public static boolean isArray(Field f) {
		if (f.getType().isArray())
			return true;
		return false;
	}

	public static boolean isCollection(Field f) {
		// for List, Set
		if (Collection.class.isAssignableFrom(f.getType()))
			return true;

		// for Map
		if (f.getType().equals(Map.class))
			return true;

		return false;
	}

	public static boolean isBoolean(Field field) {
		if (field.getType().equals(Boolean.class)) {
			return true;
		}
		return false;
	}

	public static boolean isDate(Field field) {
		if (field.getType().equals(java.util.Date.class)) {
			return true;
		}
		return false;
	}

	/**
	 * Primitive or String or Collection or Boolean or Date Types
	 * 
	 * @param field
	 * @return
	 */
	public static boolean isPredfinedObject(Field field) {
		if (ClassUtils.isPrimitiveOrWrapper(field.getType()) || isString(field) || isCollection(field)
				|| isBoolean(field) || isDate(field) || isArray(field)) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param obj
	 * @return
	 */
	public static List<Field> getDeclaredFields(Object obj) {
		return getDeclaredFields(obj.getClass());
	}

	/**
	 * 
	 * @param obj
	 * @return
	 */
	public static List<Field> getDeclaredFields(Class<?> clazz) {
		return Arrays.asList(clazz.getDeclaredFields());
	}

	/**
	 * fieldType.getType() class
	 * 
	 * @param f
	 * @return
	 */
	public static List<Field> getDeclaredFields(Field f) {
		return Arrays.asList(f.getType().getDeclaredFields());
	}

	/**
	 * 
	 * @param obj
	 * @return
	 */
	public static List<Field> getSuperClassDeclaredFields(Object obj) {
		if (isContainSuperClass(obj)) {
			return Arrays.asList(obj.getClass().getSuperclass().getDeclaredFields());
		}
		return null;
	}

	/**
	 * fieldType.getType() class
	 * 
	 * @param beanClass
	 * @return
	 */
	public static List<Field> getSuperClassDeclaredFields(Field f) {
		if (containsSuperClass(f)) {
			return Arrays.asList(f.getType().getSuperclass().getDeclaredFields());
		}
		return null;
	}

	/**
	 * Returns the value of the field represented by this {@code Field}, on the
	 * specified object. <br>
	 * Please go through {@link Field#get()} method
	 * 
	 * @param obj
	 * @param field
	 * @return Field value as object
	 */
	public static Object getFieldValueAsObject(Object obj, Field field) {
		// Set Access true
		field.setAccessible(true); // Additional line
		Object fieldObj = null;
		try {
			fieldObj = field.get(obj);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			System.err.println(obj);
			System.err.println(field);
		}
		return fieldObj;
	}

	/**
	 * 
	 * @param obj
	 * @return List of all field's values as string type
	 */
	public static ArrayList<String> getAllFieldsValues(Object obj) {
		if (obj == null)
			return null;
		ArrayList<Field> fields = new ArrayList<>();
		fields.addAll(getDeclaredFields(obj));
		return getAllFieldsValues(obj, fields);
	}

	/**
	 * 
	 * @param <T>
	 * @param obj
	 * @return List of all field's values as string type
	 */
	public static <T> ArrayList<String> getAllFieldsValuesAsEmpty(Class<T> clazz) {
		ArrayList<Field> fields = new ArrayList<>();
		fields.addAll(getDeclaredFields(clazz));
		return getAllFieldsValuesAsEmpty(fields);
	}

	/**
	 * 
	 * @param bean
	 * @return List of all field's values as string type
	 */
	public static ArrayList<String> getAllFieldsValuesIncludeSuperClass(Object bean) {
		ArrayList<Field> fields = new ArrayList<>();
		List<Field> fds = getSuperClassDeclaredFields(bean);
		if (fds != null) {
			fields.addAll(fds);
		}
		fields.addAll(Arrays.asList(bean.getClass().getDeclaredFields()));
		// System.out.println(beanClass.getClass().getDeclaredFields());
		return getAllFieldsValues(bean, fields);
	}

	/**
	 * 
	 * @param beanClass
	 * @return List of all field's values as string type
	 */
	public static ArrayList<String> getAllFieldsValuesIncludeSuperClass(Object model, Field field) {
		Object obj = getFieldValueAsObject(model, field);
		return getAllFieldsValuesIncludeSuperClass(obj);
	}

	/**
	 * 
	 * @param bean
	 * @return List of all field's values as string type
	 */
	public static ArrayList<String> getAllFieldsValues(Object bean, List<Field> fields) {
		ArrayList<String> values = new ArrayList<String>();
		Object obj = null;
		Object nestObj = null;
		List<Field> nestObjFields;
		for (Field field : fields) {
			// System.out.println("Field : " + field.getType());
			if (isPredfinedObject(field)) {
				obj = getFieldValueAsObject(bean, field);
				if (obj == null)
					values.add("");
				else if(isArray(field)) {
					values.add(Arrays.deepToString((String[])obj));
				}
				else
					values.add(obj + "");
			} else {
				try {
					// System.out.println(field.get(bean));
					nestObj = getFieldValueAsObject(bean, field);
					nestObjFields = getDeclaredFields(field.getType());
					// if the field or object is null
					if (nestObj == null) {
						values.addAll(getAllFieldsValuesAsEmpty(nestObjFields));
					} else
						values.addAll(getAllFieldsValues(nestObj, nestObjFields));
				} catch (Exception e) {
					e.printStackTrace();
				}
				continue;
			}
		}
		return values;
	}

	/**
	 * 
	 * @param bean
	 * @return List of all field's values as string type
	 */
	public static ArrayList<String> getAllFieldsValuesAsEmpty(List<Field> fields) {
		ArrayList<String> values = new ArrayList<String>();
		fields.forEach(t -> {
			if (isPredfinedObject(t))
				values.add("");
			else
				values.addAll(getAllFieldsValuesAsEmpty(t.getClass()));
		});
		return values;
	}

	/**
	 * An automated Sql Escape Apostrophe before preparing an Object as Sql insert
	 * query
	 * 
	 * @param bean
	 * @return List of all field's values as string type
	 */
	public static Object escapeSql_Apostrophe(Object bean) {
		List<Field> fields = getDeclaredFields(bean);
		for (Field field : fields) {
			field.setAccessible(true); // Additional line
			String val = null;
			// System.out.println("type : " + field.getType().getSimpleName());
			if (isPredfinedObject(field)) {
				try {
					Object obj = field.get(bean);
					if (obj != null) {
						val = obj + "";
						if (val.contains("'")) {
							val = val.replaceAll("'", "''");
							String type = field.getType().getSimpleName();
							if (type.equals(String.class.getSimpleName())) {
								field.set(bean, val);
							}
							if (type.equals(Character.class.getSimpleName())) {
								field.setChar(bean, val.charAt(0));
							}
						}
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			} else {
				try {
					System.out.println(field.get(bean));
					System.out.println(getAllFieldsValues(field.get(bean)));
					continue;
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return bean;
	}

	/**
	 * 
	 * @param beanClass
	 * @return List of all fields names
	 */
	public static ArrayList<String> getAllFieldsSimpleNames(List<Field> fields) {
		ArrayList<String> fieldNames = new ArrayList<String>();
		for (Field field : fields) {
			// if field has a super class
			// System.out.println(field.getType().getSuperclass().getSimpleName());
			if (isPredfinedObject(field)) {
				fieldNames.add(field.getName());
			} else {
				// if field is not a predefined object
				fieldNames.addAll(getAllFieldsSimpleNames(Arrays.asList(field.getType().getDeclaredFields())));
			}
		}
		return fieldNames;
	}

	/**
	 * 
	 * @param beanClass
	 * @return List of all fields names
	 */
	public static ArrayList<String> getDeclaredFieldsSimpleNames(Class<?> beanClass) {
		Field[] fields = beanClass.getDeclaredFields();
		return getAllFieldsSimpleNames(Arrays.asList(fields));
	}

	/**
	 * 
	 * @param beanClass
	 * @return List of all fields names
	 */
	public static ArrayList<String> getAllFieldsSimpleNamesIncludeSuperClass(Class<?> beanClass) {
		ArrayList<Field> fields = new ArrayList<>();
		List<Field> fd = getSuperClassDeclaredFields(beanClass);
		if (fd != null) {
			fields.addAll(fd);
		}
		fields.addAll(getDeclaredFields(beanClass));
		return getAllFieldsSimpleNames(fields);
	}

	/**
	 * Object.class is the super class of all classes. <br>
	 * Retrieves super class of given class. <br>
	 * If this class extends no other class then Object.class will be retrieved.
	 * 
	 * @param obj
	 * @return List of all fields names
	 */
	public static boolean isContainSuperClass(Object obj) {
		// System.out.println(obj.getClass());
		// System.out.println(obj);
		// System.out.println(obj.getClass().getSuperclass());
		if (obj.getClass().getSuperclass().equals(Object.class)) {
			return false;
		}
		return true;
	}

	/**
	 * Object.class is the super class of all classes. <br>
	 * Other than Object.class verifies if this field.getType() class extends any
	 * other class
	 * 
	 * @param fieldType
	 * @return
	 */
	public static boolean containsSuperClass(Field fieldType) {
		// System.out.println(fieldType.getType().getSuperclass().getSimpleName());
		if (fieldType.getType().getSuperclass().equals(Object.class)) {
			return false;
		}

		return true;
	}

	/**
	 * Checks a field name exists in specific class or not.
	 * 
	 * @param beanClass
	 * @param fieldName
	 * @return true if bean class contains given field
	 */
	public static boolean containsField(Class<?> beanClass, String fieldName) {
		boolean b = getDeclaredFieldsSimpleNames(beanClass).contains(fieldName);
		return b;
	}

	// /**
	// * an object field's null vaidation
	// *
	// * @param obj
	// * @return
	// */
	// public static Object nullRetention(Object obj) {
	// for (Field f : obj.getClass().getFields()) {
	// f.setAccessible(true);
	// try {
	// if (f.get(obj) == null) {
	// f.set(obj, getDefaultValueForType(f.getType()));
	// }
	// } catch (IllegalArgumentException | IllegalAccessException e) {
	// e.printStackTrace();
	// }
	// }
	// return obj;
	// }
	//
	// private static Object getDefaultValueForType(Class<?> type) {
	//
	// return null;
	// }

}
