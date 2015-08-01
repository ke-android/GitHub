package com.kewensheng.tool;
//package com.kewensheng.tool;
//
//import java.io.IOException;
//import java.lang.reflect.ParameterizedType;
//import java.lang.reflect.Type;
//import java.math.BigDecimal;
//import java.sql.Timestamp;
//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Collection;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.commons.beanutils.ConvertUtils;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.codehaus.jackson.JsonNode;
//import org.codehaus.jackson.JsonParseException;
//import org.codehaus.jackson.JsonParser;
//import org.codehaus.jackson.map.DeserializationConfig;
//import org.codehaus.jackson.map.JsonMappingException;
//import org.codehaus.jackson.map.ObjectMapper;
//import org.codehaus.jackson.map.SerializationConfig;
//import org.codehaus.jackson.map.annotate.JsonSerialize;
//import org.codehaus.jackson.type.JavaType;
//
//public final class ObjectMapperUtil{
//	protected static final Log LOG = LogFactory.getLog(ObjectMapperUtil.class);
//	private static final ObjectMapper objectMapper=new ObjectMapper();
//	private static final DateFormat defalut=new MutilSimpleDateFormat();
//    static{
//        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
//        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES,true);
//        objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
//        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
//        objectMapper.setDateFormat(defalut);
//        ConvertUtils.register(new BigDecimalConverter(),BigDecimal.class);
//        ConvertUtils.register(new DateConverter(),Date.class);
//    }
//    private ObjectMapperUtil(){}
//	private static Class<?>[]  getGenericClass(Type type){
//        if(ParameterizedType.class.isInstance(type)){
//            ParameterizedType pt = (ParameterizedType) type;
//            //pt.getRawType()
//            Class<?>[] classes=new  Class[pt.getActualTypeArguments().length];
//            for(int j=0,l=pt.getActualTypeArguments().length;j<l;j++){
//                classes[j]=(Class<?>)pt.getActualTypeArguments()[j];
//            }
//            return classes;
//        }
//        return null;
//    }
//	public static String toJson(Object bean){
//		try{
//		  return objectMapper.writeValueAsString(bean);
//		}catch(Exception e){
//			LOG.error(e.getMessage(),e);
//			throw new RuntimeException("对象转换json出错");
//		}
//	}
//	//{serviceKeys:[],params:[]} {data:obj,pageInfo:fm.pageInfo}
//	private static final String PARAM="data";
//	public static boolean mayBeJSON(String jsonstr) {
//		if (jsonstr.isEmpty()) {
//			return false;
//		}
//		char head = jsonstr.charAt(0);
//		return head == '[' || head == '{';
//	}
//	public static <T> T readValue(String jsonstr,Class<T> clazz){
//		try{
//			JsonNode json=objectMapper.readTree(jsonstr);
//			Object object = null;
//			if (Collection.class.isAssignableFrom(clazz)) {
//				if (json != null && json.isArray()){
//					JavaType javaType = objectMapper.getTypeFactory().constructParametricType(clazz,getGenericClass(clazz)[0]);
//					object = objectMapper.readValue(json, javaType);
//				}
//			} else if (Map.class.isAssignableFrom(clazz)) {
//				Class<?>[] orgClass = getGenericClass(clazz);
//				JavaType javaType = objectMapper.getTypeFactory()
//						.constructParametricType(HashMap.class, orgClass[0],
//								orgClass[1]);
//				object = objectMapper.readValue(json, javaType);
//			} else {
//				if(clazz.isPrimitive() || Date.class.isAssignableFrom(clazz)
//						|| Timestamp.class.isAssignableFrom(clazz)
//						|| Number.class.isAssignableFrom(clazz)) {
//					if(json.isValueNode()){
//						object = ConvertUtils.convert(json.asText(), clazz);
//					}else{
//						object = ConvertUtils.convert(json.getTextValue(), clazz);
//					}
//				} else if (String.class.isInstance(clazz)) {
//					object = json.getTextValue();
//				} else {
//					object = objectMapper.readValue(json, clazz);
//				}
//			}
//			return (T)object;
//		}catch(Exception e){
//			throw new RuntimeException(e.getMessage(),e);
//		}
//	}
//	public static Object[] readValue(
//			Class<?>[] clazzes,
//			Type[] genericParameterTypes,
//			String[] paramNames,
//			String jsonstr){
//		Object[] object=new Object[paramNames.length];
//		jsonstr=jsonstr.trim();
//		if(mayBeJSON(jsonstr)){
//			try {
//				JsonNode json=null,jsonNode=objectMapper.readTree(jsonstr);
//				Class<?> clazz=null;
//				String paramName=null;
//				Type type=null;
//				boolean hasPage=false;
//				json=jsonNode.get(PARAM);
//				if(json!=null&&jsonNode.get("pageInfo")!=null){
//					hasPage=true; 
//				}
//				for(int i=0;i<clazzes.length;i++){
//				   clazz=clazzes[i];
//				   paramName=paramNames[i];
//				   type=genericParameterTypes[i];
//				   if(clazzes.length==1){
//					   //if(hasPage&&!PageInfo.class.isAssignableFrom(clazz)){
//					   if(hasPage){
//						 json=jsonNode.get(PARAM);  
//					   }else{
//				         json=jsonNode;
//					   }
//				   }else{
//					  //if(hasPage&&!PageInfo.class.isAssignableFrom(clazz)){
//					  if(hasPage){
//						json=jsonNode.get(PARAM); 
//					  }else{
//					    json=jsonNode.get(paramName);  
//					  }
//				   }
//				   if(json!=null){
//					   object[i]=readValue(clazz,type,paramName,json,objectMapper);
//				   }
//				}
//			}catch(Exception e){
//				throw new RuntimeException(e.getMessage(),e);
//			}
//		}else{
//			object[0]=ConvertUtils.convert(jsonstr,clazzes[0]);
//		}
//		return object;
//	}
//
//	private static Object readValue(Class<?> clazz, Type type, String paramName,
//			JsonNode json, ObjectMapper objectMapper)
//			throws JsonParseException, JsonMappingException, IOException {
//		Object object = null;
//		if (Collection.class.isAssignableFrom(clazz)) {
//			if (json != null && json.isArray()) {
//				JavaType javaType = objectMapper.getTypeFactory()
//						.constructParametricType(clazz,
//								getGenericClass(type)[0]);
//				object = objectMapper.readValue(json, javaType);
//			} else {
//				throw new RuntimeException(paramName
//						+ " and submission is not an array ");
//			}
//		} else if (Map.class.isAssignableFrom(clazz)) {
//			Class<?>[] orgClass = getGenericClass(type);
//			JavaType javaType = objectMapper.getTypeFactory()
//					.constructParametricType(HashMap.class, orgClass[0],
//							orgClass[1]);
//			object = objectMapper.readValue(json, javaType);
//		} else {
//			if(clazz.isPrimitive() || Date.class.isAssignableFrom(clazz)
//					|| Timestamp.class.isAssignableFrom(clazz)
//					|| Number.class.isAssignableFrom(clazz)) {
//				if(json.isObject()){
//					json=json.get(paramName);
//				}else if(json.isValueNode()){
//					return ConvertUtils.convert(json.asText(), clazz);
//				}
//				object = ConvertUtils.convert(json.asText(), clazz);
//			} else if (String.class.equals(clazz)) {
//				if(json.isObject()){
//					json=json.get(paramName);
//				}
//				object = json.getTextValue();
//			} else if (clazz.isArray()) {
//				if(json.isObject()){
//					json=json.get(paramName);
//				}
//				object = objectMapper.readValue(json, clazz);
//			} else {
//				object = objectMapper.readValue(json, clazz);
//			}
//		}
//		return object;
//	}
//	private static final class MutilSimpleDateFormat extends SimpleDateFormat{
//		private static final long serialVersionUID = -6021616244330475107L;
//		private static final String DATE_STR="yyyy-MM-dd";
//        private static final String DATE_TIME_STR="yyyy-MM-dd HH:mm:ss";
//        private static final String DATE_LINE_STR="yyyyMMdd";
//        private static final String DATE_TIME__LINE_STR="yyyyMMddHHmmss";
//        private static final DateFormat defalut=new SimpleDateFormat(DATE_STR);
//        private static final DateFormat defalutTime=new SimpleDateFormat(DATE_TIME_STR);
//        private static final DateFormat defalutLine=new SimpleDateFormat(DATE_LINE_STR);
//        private static final DateFormat defalutTimeLine=new SimpleDateFormat(DATE_TIME__LINE_STR);
//        public MutilSimpleDateFormat(){
//        	super(DATE_STR);
//        }
//        public Date parse(String source) throws ParseException{
//            if(source==null) return null;
//            if(DATE_STR.length()==source.length()){
//               return  defalut.parse(source);
//            }else if(DATE_TIME_STR.length()==source.length()){
//               return  defalutTime.parse(source);
//            }else if(DATE_LINE_STR.length()==source.length()){
//               return  defalutLine.parse(source);
//            }else if(DATE_TIME__LINE_STR.length()==source.length()){
//               return  defalutTimeLine.parse(source);
//            }
//            return  defalut.parse(source);
//        }
//       
//    }
//}
