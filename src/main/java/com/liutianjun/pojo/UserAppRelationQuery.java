package com.liutianjun.pojo;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.liutianjun.pojo.ApplicationQuery.Criteria;

public class UserAppRelationQuery {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer pageNo = 1;

    protected Integer startRow;

    protected Integer pageSize = 10;

    protected String fields;

    public UserAppRelationQuery() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo=pageNo;
        this.startRow = (pageNo-1)*this.pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setStartRow(Integer startRow) {
        this.startRow=startRow;
    }

    public Integer getStartRow() {
        return startRow;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize=pageSize;
        this.startRow = (pageNo-1)*this.pageSize;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setFields(String fields) {
        this.fields=fields;
    }

    public String getFields() {
        return fields;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }
       

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Integer value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Integer value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Integer value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Integer value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Integer> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Integer> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Integer value1, Integer value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andAppIdIsNull() {
            addCriterion("app_id is null");
            return (Criteria) this;
        }

        public Criteria andAppIdIsNotNull() {
            addCriterion("app_id is not null");
            return (Criteria) this;
        }

        public Criteria andAppIdEqualTo(Integer value) {
            addCriterion("app_id =", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotEqualTo(Integer value) {
            addCriterion("app_id <>", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdGreaterThan(Integer value) {
            addCriterion("app_id >", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("app_id >=", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdLessThan(Integer value) {
            addCriterion("app_id <", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdLessThanOrEqualTo(Integer value) {
            addCriterion("app_id <=", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdIn(List<Integer> values) {
            addCriterion("app_id in", values, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotIn(List<Integer> values) {
            addCriterion("app_id not in", values, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdBetween(Integer value1, Integer value2) {
            addCriterion("app_id between", value1, value2, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotBetween(Integer value1, Integer value2) {
            addCriterion("app_id not between", value1, value2, "appId");
            return (Criteria) this;
        }

        public Criteria andAppNameIsNull() {
            addCriterion("app_name is null");
            return (Criteria) this;
        }

        public Criteria andAppNameIsNotNull() {
            addCriterion("app_name is not null");
            return (Criteria) this;
        }

        public Criteria andAppNameEqualTo(String value) {
            addCriterion("app_name =", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameNotEqualTo(String value) {
            addCriterion("app_name <>", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameGreaterThan(String value) {
            addCriterion("app_name >", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameGreaterThanOrEqualTo(String value) {
            addCriterion("app_name >=", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameLessThan(String value) {
            addCriterion("app_name <", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameLessThanOrEqualTo(String value) {
            addCriterion("app_name <=", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameLike(String value) {
            addCriterion("app_name like", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameNotLike(String value) {
            addCriterion("app_name not like", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameIn(List<String> values) {
            addCriterion("app_name in", values, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameNotIn(List<String> values) {
            addCriterion("app_name not in", values, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameBetween(String value1, String value2) {
            addCriterion("app_name between", value1, value2, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameNotBetween(String value1, String value2) {
            addCriterion("app_name not between", value1, value2, "appName");
            return (Criteria) this;
        }
        public Criteria andAppNameNullIn(List<String> values) {
        	String value=toValues(values);
        	String sql=" (app_name in ("+value+") ";
        	if(values.contains("null")) {
        		sql+=" or app_name is null ";
    		}
        	sql+=")";
            addCriterion(sql);
            return (Criteria) this;
        }


        public Criteria andCreatorIsNull() {
            addCriterion("creator is null");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNotNull() {
            addCriterion("creator is not null");
            return (Criteria) this;
        }

        public Criteria andCreatorEqualTo(String value) {
            addCriterion("creator =", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotEqualTo(String value) {
            addCriterion("creator <>", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThan(String value) {
            addCriterion("creator >", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThanOrEqualTo(String value) {
            addCriterion("creator >=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThan(String value) {
            addCriterion("creator <", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThanOrEqualTo(String value) {
            addCriterion("creator <=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLike(String value) {
            addCriterion("creator like", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotLike(String value) {
            addCriterion("creator not like", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorIn(List<String> values) {
            addCriterion("creator in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotIn(List<String> values) {
            addCriterion("creator not in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorBetween(String value1, String value2) {
            addCriterion("creator between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotBetween(String value1, String value2) {
            addCriterion("creator not between", value1, value2, "creator");
            return (Criteria) this;
        }
        public Criteria andCreatorNullIn(List<String> values) {
        	String value=toValues(values);
        	String sql=" (creator in ("+value+") ";
        	if(values.contains("null")) {
        		sql+=" or creator is null ";
    		}
        	sql+=")";
            addCriterion(sql);
            return (Criteria) this;
        }

        public Criteria andAppTypeIsNull() {
            addCriterion("app_type is null");
            return (Criteria) this;
        }

        public Criteria andAppTypeIsNotNull() {
            addCriterion("app_type is not null");
            return (Criteria) this;
        }

        public Criteria andAppTypeEqualTo(String value) {
            addCriterion("app_type =", value, "appType");
            return (Criteria) this;
        }

        public Criteria andAppTypeNotEqualTo(String value) {
            addCriterion("app_type <>", value, "appType");
            return (Criteria) this;
        }

        public Criteria andAppTypeGreaterThan(String value) {
            addCriterion("app_type >", value, "appType");
            return (Criteria) this;
        }

        public Criteria andAppTypeGreaterThanOrEqualTo(String value) {
            addCriterion("app_type >=", value, "appType");
            return (Criteria) this;
        }

        public Criteria andAppTypeLessThan(String value) {
            addCriterion("app_type <", value, "appType");
            return (Criteria) this;
        }

        public Criteria andAppTypeLessThanOrEqualTo(String value) {
            addCriterion("app_type <=", value, "appType");
            return (Criteria) this;
        }

        public Criteria andAppTypeLike(String value) {
            addCriterion("app_type like", value, "appType");
            return (Criteria) this;
        }

        public Criteria andAppTypeNotLike(String value) {
            addCriterion("app_type not like", value, "appType");
            return (Criteria) this;
        }

        public Criteria andAppTypeIn(List<String> values) {
            addCriterion("app_type in", values, "appType");
            return (Criteria) this;
        }
        
        public Criteria andAppTypelengthNull() {
          	 addCriterion("(LENGTH(app_type)<1 or keywords is NULL)");
          	  return (Criteria) this;
          }
        public Criteria andAppTypeNotIn(List<String> values) {
            addCriterion("app_type not in", values, "appType");
            return (Criteria) this;
        }

        public Criteria andAppTypeBetween(String value1, String value2) {
            addCriterion("app_type between", value1, value2, "appType");
            return (Criteria) this;
        }

        public Criteria andAppTypeNotBetween(String value1, String value2) {
            addCriterion("app_type not between", value1, value2, "appType");
            return (Criteria) this;
        }
        public Criteria andAppTypeNullIn(List<String> values) {
        	String value=toValues(values);
        	String sql=" (app_type in ("+value+") ";
        	if(values.contains("null")) {
        		sql+=" or app_type is null ";
    		}
        	sql+=")";
            addCriterion(sql);
            return (Criteria) this;
        }

        public Criteria andIsAsyncIsNull() {
            addCriterion("is_async is null");
            return (Criteria) this;
        }

        public Criteria andIsAsyncIsNotNull() {
            addCriterion("is_async is not null");
            return (Criteria) this;
        }

        public Criteria andIsAsyncEqualTo(Integer value) {
            addCriterion("is_async =", value, "isAsync");
            return (Criteria) this;
        }

        public Criteria andIsAsyncNotEqualTo(Integer value) {
            addCriterion("is_async <>", value, "isAsync");
            return (Criteria) this;
        }

        public Criteria andIsAsyncGreaterThan(Integer value) {
            addCriterion("is_async >", value, "isAsync");
            return (Criteria) this;
        }

        public Criteria andIsAsyncGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_async >=", value, "isAsync");
            return (Criteria) this;
        }

        public Criteria andIsAsyncLessThan(Integer value) {
            addCriterion("is_async <", value, "isAsync");
            return (Criteria) this;
        }

        public Criteria andIsAsyncLessThanOrEqualTo(Integer value) {
            addCriterion("is_async <=", value, "isAsync");
            return (Criteria) this;
        }

        public Criteria andIsAsyncIn(List<String> values) {
            addCriterion("is_async in", values, "isAsync");
            return (Criteria) this;
        }
        public Criteria andIsAsyncIn1(List<String> values) {
        	addCriterion("is_async in", values, "isAsync");
        	return (Criteria) this;
        }
        public Criteria andIsAsyncNullIn(List<String> values) {
        	boolean isNull=false;
        	if(values.contains("null")) {
        		isNull=true;
        		values.remove("null");
    		}
        	String value=toValues(values);
        	String sql=" (is_async in ("+value+") ";
        	if(isNull) {
        		sql+=" or is_async is null ";
    		}
        	sql+=")";
            addCriterion(sql);
            return (Criteria) this;
        }
        

        public Criteria andIsAsyncNotIn(List<Integer> values) {
            addCriterion("is_async not in", values, "isAsync");
            return (Criteria) this;
        }

        public Criteria andIsAsyncBetween(Integer value1, Integer value2) {
            addCriterion("is_async between", value1, value2, "isAsync");
            return (Criteria) this;
        }

        public Criteria andIsAsyncNotBetween(Integer value1, Integer value2) {
            addCriterion("is_async not between", value1, value2, "isAsync");
            return (Criteria) this;
        }

        public Criteria andKeywordsIsNull() {
            addCriterion("keywords is null");
            return (Criteria) this;
        }

        public Criteria andKeywordsIsNotNull() {
            addCriterion("keywords is not null");
            return (Criteria) this;
        }
        
        public Criteria andKeywordslengthNull() {
        	 addCriterion("(LENGTH(keywords)<1 or keywords is NULL)");
        	  return (Criteria) this;
        }
        public Criteria andKeywordsnullor(List<String> values) {
        	addCriterion("(LENGTH(keywords)<1 or keywords is NULL or keywords in",values,"keywords");
        	 return (Criteria) this;
        }
        public Criteria andKeywordsEqualTo(String value) {
            addCriterion("keywords =", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsNotEqualTo(String value) {
            addCriterion("keywords <>", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsGreaterThan(String value) {
            addCriterion("keywords >", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsGreaterThanOrEqualTo(String value) {
            addCriterion("keywords >=", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsLessThan(String value) {
            addCriterion("keywords <", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsLessThanOrEqualTo(String value) {
            addCriterion("keywords <=", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsLike(String value) {
            addCriterion("keywords like", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsNotLike(String value) {
            addCriterion("keywords not like", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsIn(List<String> values) {
            addCriterion("keywords in", values, "keywords");
            return (Criteria) this;
        }
       public Criteria andKeywordsLength() {
    	   addCriterion("LENGTH(keywords)<", 1, "keywords");
    	   return (Criteria) this;
       }

        public Criteria andKeywordsNotIn(List<String> values) {
            addCriterion("keywords not in", values, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsBetween(String value1, String value2) {
            addCriterion("keywords between", value1, value2, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsNotBetween(String value1, String value2) {
            addCriterion("keywords not between", value1, value2, "keywords");
            return (Criteria) this;
        }
        public Criteria andKeywordsNullIn(List<String> values) {
        	String value=toValues(values);
        	String sql=" (keywords in ("+value+") ";
        	if(values.contains("null")) {
        		sql+=" or keywords is null ";
    		}
        	sql+=")";
            addCriterion(sql);
            return (Criteria) this;
        }

        
        
        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<String> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }
        
        public Criteria andCreateTimeNullIn(List<String> values) {
        	String value="";
        	for(String val:values) {
        		value+=" '"+val+"', ";	
        	}
        	value=value.substring(0,value.length()-2);
        	String sql=" (create_time in ("+value+") ";
        	if(values.contains("null")) {
        		sql+=" or create_time is null ";
    		}
        	sql+=")";
            addCriterion(sql);
            return (Criteria) this;
        }
        /*public Criteria andCreateTimeNullIn(List<String> values) {
        	String value="";
        	for(String val:values) {
        		value+=" '"+val+"', ";	
        	}
        	value=value.substring(0,value.length()-2);
        	String sql=" (";
        	sql+=" create_time like ";
        	for(int i=0;i<values.size();i++) {
        		sql+= " '"+values.get(i)+"%'" ;
        		if(i<values.size()-1) {
        			sql+=" or ";
        		}
        		
        	}
        	sql+=")";
        	if(values.contains("null")) {
        		sql+=" or create_time is null ";
    		}
        	
            addCriterion(sql);
            return (Criteria) this;
        }
*/
        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andAppOverviewIsNull() {
            addCriterion("app_overview is null");
            return (Criteria) this;
        }

        public Criteria andAppOverviewIsNotNull() {
            addCriterion("app_overview is not null");
            return (Criteria) this;
        }

        public Criteria andAppOverviewEqualTo(String value) {
            addCriterion("app_overview =", value, "appOverview");
            return (Criteria) this;
        }

        public Criteria andAppOverviewNotEqualTo(String value) {
            addCriterion("app_overview <>", value, "appOverview");
            return (Criteria) this;
        }

        public Criteria andAppOverviewGreaterThan(String value) {
            addCriterion("app_overview >", value, "appOverview");
            return (Criteria) this;
        }

        public Criteria andAppOverviewGreaterThanOrEqualTo(String value) {
            addCriterion("app_overview >=", value, "appOverview");
            return (Criteria) this;
        }

        public Criteria andAppOverviewLessThan(String value) {
            addCriterion("app_overview <", value, "appOverview");
            return (Criteria) this;
        }

        public Criteria andAppOverviewLessThanOrEqualTo(String value) {
            addCriterion("app_overview <=", value, "appOverview");
            return (Criteria) this;
        }

        public Criteria andAppOverviewLike(String value) {
            addCriterion("app_overview like", value, "appOverview");
            return (Criteria) this;
        }

        public Criteria andAppOverviewNotLike(String value) {
            addCriterion("app_overview not like", value, "appOverview");
            return (Criteria) this;
        }

        public Criteria andAppOverviewIn(List<String> values) {
            addCriterion("app_overview in", values, "appOverview");
            return (Criteria) this;
        }
        
        public Criteria andAppOverviewNullIn(List<String> values) {
        	String value="";
        	for(String val:values) {
        		value+=" '"+val+"', ";	
        	}
        	value=value.substring(0,value.length()-2);
        	String sql=" (app_overview in ("+value+") ";
        	if(values.contains("null")) {
        		sql+=" or app_overview is null ";
    		}
        	sql+=")";
            addCriterion(sql);
            return (Criteria) this;
        }
        
        public Criteria andAppOverviewlengthNull() {
       	 addCriterion("(LENGTH(app_overview)<1 or keywords is NULL)");
       	  return (Criteria) this;
       }

        public Criteria andAppOverviewNotIn(List<String> values) {
            addCriterion("app_overview not in", values, "appOverview");
            return (Criteria) this;
        }

        public Criteria andAppOverviewBetween(String value1, String value2) {
            addCriterion("app_overview between", value1, value2, "appOverview");
            return (Criteria) this;
        }

        public Criteria andAppOverviewNotBetween(String value1, String value2) {
            addCriterion("app_overview not between", value1, value2, "appOverview");
            return (Criteria) this;
        }
        
        public Criteria andOrSearch(String appName) {
        	addCriterion("(app_name like '%"+appName+"%' or creator like '%"+appName+"%' or "
        			+ "keywords like '%" + appName +"%' or app_overview like '%"+appName+"%')");
        	return (Criteria) this;
        }
        
        
        public String toValues(List<String> values ) {
        	String value="";
        	for(String val:values) {
        		value+=" '"+val+"', ";
        	}
        	value=value.substring(0,value.length()-2);
        	return value;
        }
        
        
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}