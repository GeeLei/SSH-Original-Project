package core.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import core.support.BaseParameter;

public class BaseParameterBuilder {

	private BaseParameter bp;
	
	private BaseParameterBuilder(){
		bp = new BaseParameter();
	}
	
	public static BaseParameterBuilder  newInstance(){
		return new BaseParameterBuilder();
	}
	
	public BaseParameter build(){
		return bp;
	}
	
	public BaseParameterBuilder flag(String flag){
		bp.setFlag(flag);
		return this;
	}
	
	public BaseParameterBuilder eq(String field,Object value){
		addCondition("eq",field,value);
		return this;
	}
	
	public BaseParameterBuilder ne(String field,Object value){
		addCondition("ne",field,value);
		return this;
	}
	
	public BaseParameterBuilder gt(String field,Object value){
		addCondition("gt",field,value);
		return this;
	}
	
	public BaseParameterBuilder ge(String field,Object value){
		addCondition("ge",field,value);
		return this;
	}
	
	public BaseParameterBuilder lt(String field,Object value){
		addCondition("lt",field,value);
		return this;
	}
	
	public BaseParameterBuilder le(String field,Object value){
		addCondition("le",field,value);
		return this;
	}
	
	public BaseParameterBuilder like(String field,Object value){
		addCondition("like",field,value);
		return this;
	}
	
	public BaseParameterBuilder in(String field,List value){
		List list = Collections.EMPTY_LIST;
		if(value != null){
			list = value;
		}
		addCondition("in",field,list);
		return this;
	}
	
	public BaseParameterBuilder in(String field,Object... value){
		List list = Collections.EMPTY_LIST;
		if(value != null){
			list = Arrays.asList(value);
		}
		addCondition("in",field,list);
		return this;
	}
	
	public BaseParameterBuilder notIn(String field,List value){
		List list = Collections.EMPTY_LIST;
		if(value != null){
			list = value;
		}
		addCondition("notIn",field,list);
		return this;
	}
	
	public BaseParameterBuilder notIn(String field,Object... value){
		List list = Collections.EMPTY_LIST;
		if(value != null){
			list = Arrays.asList(value);
		}
		addCondition("notIn",field,list);
		return this;
	}
	
	public BaseParameterBuilder isNull(String field){
		addCondition("isNull",field,null);
		return this;
	}
	
	public BaseParameterBuilder isNotNull(String field){
		addCondition("isNotNull",field,null);
		return this;
	}
	
	public BaseParameterBuilder page(int pageNo,int pageSize){
		int firstIndex = ( pageNo - 1 ) * pageSize;
		bp.setFirstResult(firstIndex);
		bp.setMaxResults(pageSize);
		return this;
	}
	
	public BaseParameterBuilder orderByASC(String field){
		bp.getSortedConditions().put(field, BaseParameter.SORTED_ASC);
		return this;
	}
	
	public BaseParameterBuilder orderByDESC(String field){
		bp.getSortedConditions().put(field, BaseParameter.SORTED_DESC);
		return this;
	}
	
	private void addCondition(String op, String field, Object value) {
		bp.getQueryDynamicConditions().put(getCondition(op,field), value);
	}

	private String getCondition(String string, String field) {
		return "$"+string+"_"+field;
	}
}
