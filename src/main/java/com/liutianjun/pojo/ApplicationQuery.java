package com.liutianjun.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.liutianjun.pojo.UserAppRelationQuery.Criteria;

public class ApplicationQuery {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer pageNo = 1;

    protected Integer startRow;

    protected Integer pageSize = 10;

    protected String fields;

    public ApplicationQuery() {
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
        public Criteria andAppNameNullLike(String value) {
        	String sql=" app_name like '%"+value+"%' ";
        	if(value==null&&value.length()<=0) {
        		sql=" or app_name is null ";
        	}
        	addCriterion(sql);
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
        public Criteria andAppTypeIn(List<String> values) {
            addCriterion("app_type in", values, "appType");
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

        public Criteria andKeywordsIsNull() {
            addCriterion("keywords is null");
            return (Criteria) this;
        }

        public Criteria andKeywordsIsNotNull() {
            addCriterion("keywords is not null");
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

        public Criteria andVersionsIsNull() {
            addCriterion("versions is null");
            return (Criteria) this;
        }

        public Criteria andVersionsIsNotNull() {
            addCriterion("versions is not null");
            return (Criteria) this;
        }

        public Criteria andVersionsEqualTo(String value) {
            addCriterion("versions =", value, "versions");
            return (Criteria) this;
        }

        public Criteria andVersionsNotEqualTo(String value) {
            addCriterion("versions <>", value, "versions");
            return (Criteria) this;
        }

        public Criteria andVersionsGreaterThan(String value) {
            addCriterion("versions >", value, "versions");
            return (Criteria) this;
        }

        public Criteria andVersionsGreaterThanOrEqualTo(String value) {
            addCriterion("versions >=", value, "versions");
            return (Criteria) this;
        }

        public Criteria andVersionsLessThan(String value) {
            addCriterion("versions <", value, "versions");
            return (Criteria) this;
        }

        public Criteria andVersionsLessThanOrEqualTo(String value) {
            addCriterion("versions <=", value, "versions");
            return (Criteria) this;
        }

        public Criteria andVersionsLike(String value) {
            addCriterion("versions like", value, "versions");
            return (Criteria) this;
        }

        public Criteria andVersionsNotLike(String value) {
            addCriterion("versions not like", value, "versions");
            return (Criteria) this;
        }

        public Criteria andVersionsIn(List<String> values) {
            addCriterion("versions in", values, "versions");
            return (Criteria) this;
        }

        public Criteria andVersionsNotIn(List<String> values) {
            addCriterion("versions not in", values, "versions");
            return (Criteria) this;
        }

        public Criteria andVersionsBetween(String value1, String value2) {
            addCriterion("versions between", value1, value2, "versions");
            return (Criteria) this;
        }

        public Criteria andVersionsNotBetween(String value1, String value2) {
            addCriterion("versions not between", value1, value2, "versions");
            return (Criteria) this;
        }

        public Criteria andIsSaveSystemIsNull() {
            addCriterion("is_save_system is null");
            return (Criteria) this;
        }

        public Criteria andIsSaveSystemIsNotNull() {
            addCriterion("is_save_system is not null");
            return (Criteria) this;
        }

        public Criteria andIsSaveSystemEqualTo(Integer value) {
            addCriterion("is_save_system =", value, "isSaveSystem");
            return (Criteria) this;
        }

        public Criteria andIsSaveSystemNotEqualTo(Integer value) {
            addCriterion("is_save_system <>", value, "isSaveSystem");
            return (Criteria) this;
        }

        public Criteria andIsSaveSystemGreaterThan(Integer value) {
            addCriterion("is_save_system >", value, "isSaveSystem");
            return (Criteria) this;
        }

        public Criteria andIsSaveSystemGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_save_system >=", value, "isSaveSystem");
            return (Criteria) this;
        }

        public Criteria andIsSaveSystemLessThan(Integer value) {
            addCriterion("is_save_system <", value, "isSaveSystem");
            return (Criteria) this;
        }

        public Criteria andIsSaveSystemLessThanOrEqualTo(Integer value) {
            addCriterion("is_save_system <=", value, "isSaveSystem");
            return (Criteria) this;
        }

        public Criteria andIsSaveSystemIn(List<Integer> values) {
            addCriterion("is_save_system in", values, "isSaveSystem");
            return (Criteria) this;
        }

        public Criteria andIsSaveSystemNotIn(List<Integer> values) {
            addCriterion("is_save_system not in", values, "isSaveSystem");
            return (Criteria) this;
        }

        public Criteria andIsSaveSystemBetween(Integer value1, Integer value2) {
            addCriterion("is_save_system between", value1, value2, "isSaveSystem");
            return (Criteria) this;
        }

        public Criteria andIsSaveSystemNotBetween(Integer value1, Integer value2) {
            addCriterion("is_save_system not between", value1, value2, "isSaveSystem");
            return (Criteria) this;
        }

        public Criteria andIsAsyncIsNull() {
            addCriterion("is_async is null");
            return (Criteria) this;
        }
        public Criteria andIsAsyncNullIn(List<String> values) {
        	String value=toValues(values);
        	String sql=" (is_async in ("+value+") ";
        	if(values.contains("null")) {
        		sql+=" or is_async is null ";
    		}
        	sql+=")";
            addCriterion(sql);
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

        public Criteria andIsDisplayIsNull() {
            addCriterion("is_display is null");
            return (Criteria) this;
        }

        public Criteria andIsDisplayIsNotNull() {
            addCriterion("is_display is not null");
            return (Criteria) this;
        }
        public Criteria andIsDisplayNullIn(List<String> values) {
        	String value=toValues(values);
        	String sql=" (is_display in ("+value+") ";
        	if(values.contains("null")) {
        		sql+=" or is_display is null ";
    		}
        	sql+=")";
            addCriterion(sql);
            return (Criteria) this;
        }
        public Criteria andIsDisplayEqualTo(Integer value) {
            addCriterion("is_display =", value, "isDisplay");
            return (Criteria) this;
        }

        public Criteria andIsDisplayNotEqualTo(Integer value) {
            addCriterion("is_display <>", value, "isDisplay");
            return (Criteria) this;
        }

        public Criteria andIsDisplayGreaterThan(Integer value) {
            addCriterion("is_display >", value, "isDisplay");
            return (Criteria) this;
        }

        public Criteria andIsDisplayGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_display >=", value, "isDisplay");
            return (Criteria) this;
        }

        public Criteria andIsDisplayLessThan(Integer value) {
            addCriterion("is_display <", value, "isDisplay");
            return (Criteria) this;
        }

        public Criteria andIsDisplayLessThanOrEqualTo(Integer value) {
            addCriterion("is_display <=", value, "isDisplay");
            return (Criteria) this;
        }

        public Criteria andIsDisplayIn(List<Integer> values) {
            addCriterion("is_display in", values, "isDisplay");
            return (Criteria) this;
        }

        public Criteria andIsDisplayNotIn(List<Integer> values) {
            addCriterion("is_display not in", values, "isDisplay");
            return (Criteria) this;
        }

        public Criteria andIsDisplayBetween(Integer value1, Integer value2) {
            addCriterion("is_display between", value1, value2, "isDisplay");
            return (Criteria) this;
        }

        public Criteria andIsDisplayNotBetween(Integer value1, Integer value2) {
            addCriterion("is_display not between", value1, value2, "isDisplay");
            return (Criteria) this;
        }

        public Criteria andComputeNodeNameIsNull() {
            addCriterion("compute_node_name is null");
            return (Criteria) this;
        }

        public Criteria andComputeNodeNameIsNotNull() {
            addCriterion("compute_node_name is not null");
            return (Criteria) this;
        }

        public Criteria andComputeNodeNameEqualTo(String value) {
            addCriterion("compute_node_name =", value, "computeNodeName");
            return (Criteria) this;
        }

        public Criteria andComputeNodeNameNotEqualTo(String value) {
            addCriterion("compute_node_name <>", value, "computeNodeName");
            return (Criteria) this;
        }

        public Criteria andComputeNodeNameGreaterThan(String value) {
            addCriterion("compute_node_name >", value, "computeNodeName");
            return (Criteria) this;
        }

        public Criteria andComputeNodeNameGreaterThanOrEqualTo(String value) {
            addCriterion("compute_node_name >=", value, "computeNodeName");
            return (Criteria) this;
        }

        public Criteria andComputeNodeNameLessThan(String value) {
            addCriterion("compute_node_name <", value, "computeNodeName");
            return (Criteria) this;
        }

        public Criteria andComputeNodeNameLessThanOrEqualTo(String value) {
            addCriterion("compute_node_name <=", value, "computeNodeName");
            return (Criteria) this;
        }

        public Criteria andComputeNodeNameLike(String value) {
            addCriterion("compute_node_name like", value, "computeNodeName");
            return (Criteria) this;
        }

        public Criteria andComputeNodeNameNotLike(String value) {
            addCriterion("compute_node_name not like", value, "computeNodeName");
            return (Criteria) this;
        }

        public Criteria andComputeNodeNameIn(List<String> values) {
            addCriterion("compute_node_name in", values, "computeNodeName");
            return (Criteria) this;
        }

        public Criteria andComputeNodeNameNotIn(List<String> values) {
            addCriterion("compute_node_name not in", values, "computeNodeName");
            return (Criteria) this;
        }

        public Criteria andComputeNodeNameBetween(String value1, String value2) {
            addCriterion("compute_node_name between", value1, value2, "computeNodeName");
            return (Criteria) this;
        }

        public Criteria andComputeNodeNameNotBetween(String value1, String value2) {
            addCriterion("compute_node_name not between", value1, value2, "computeNodeName");
            return (Criteria) this;
        }

        public Criteria andParaAddressIsNull() {
            addCriterion("para_address is null");
            return (Criteria) this;
        }

        public Criteria andParaAddressIsNotNull() {
            addCriterion("para_address is not null");
            return (Criteria) this;
        }

        public Criteria andParaAddressEqualTo(String value) {
            addCriterion("para_address =", value, "paraAddress");
            return (Criteria) this;
        }

        public Criteria andParaAddressNotEqualTo(String value) {
            addCriterion("para_address <>", value, "paraAddress");
            return (Criteria) this;
        }

        public Criteria andParaAddressGreaterThan(String value) {
            addCriterion("para_address >", value, "paraAddress");
            return (Criteria) this;
        }

        public Criteria andParaAddressGreaterThanOrEqualTo(String value) {
            addCriterion("para_address >=", value, "paraAddress");
            return (Criteria) this;
        }

        public Criteria andParaAddressLessThan(String value) {
            addCriterion("para_address <", value, "paraAddress");
            return (Criteria) this;
        }

        public Criteria andParaAddressLessThanOrEqualTo(String value) {
            addCriterion("para_address <=", value, "paraAddress");
            return (Criteria) this;
        }

        public Criteria andParaAddressLike(String value) {
            addCriterion("para_address like", value, "paraAddress");
            return (Criteria) this;
        }

        public Criteria andParaAddressNotLike(String value) {
            addCriterion("para_address not like", value, "paraAddress");
            return (Criteria) this;
        }

        public Criteria andParaAddressIn(List<String> values) {
            addCriterion("para_address in", values, "paraAddress");
            return (Criteria) this;
        }

        public Criteria andParaAddressNotIn(List<String> values) {
            addCriterion("para_address not in", values, "paraAddress");
            return (Criteria) this;
        }

        public Criteria andParaAddressBetween(String value1, String value2) {
            addCriterion("para_address between", value1, value2, "paraAddress");
            return (Criteria) this;
        }

        public Criteria andParaAddressNotBetween(String value1, String value2) {
            addCriterion("para_address not between", value1, value2, "paraAddress");
            return (Criteria) this;
        }

        public Criteria andResultAddressIsNull() {
            addCriterion("result_address is null");
            return (Criteria) this;
        }

        public Criteria andResultAddressIsNotNull() {
            addCriterion("result_address is not null");
            return (Criteria) this;
        }

        public Criteria andResultAddressEqualTo(String value) {
            addCriterion("result_address =", value, "resultAddress");
            return (Criteria) this;
        }

        public Criteria andResultAddressNotEqualTo(String value) {
            addCriterion("result_address <>", value, "resultAddress");
            return (Criteria) this;
        }

        public Criteria andResultAddressGreaterThan(String value) {
            addCriterion("result_address >", value, "resultAddress");
            return (Criteria) this;
        }

        public Criteria andResultAddressGreaterThanOrEqualTo(String value) {
            addCriterion("result_address >=", value, "resultAddress");
            return (Criteria) this;
        }

        public Criteria andResultAddressLessThan(String value) {
            addCriterion("result_address <", value, "resultAddress");
            return (Criteria) this;
        }

        public Criteria andResultAddressLessThanOrEqualTo(String value) {
            addCriterion("result_address <=", value, "resultAddress");
            return (Criteria) this;
        }

        public Criteria andResultAddressLike(String value) {
            addCriterion("result_address like", value, "resultAddress");
            return (Criteria) this;
        }

        public Criteria andResultAddressNotLike(String value) {
            addCriterion("result_address not like", value, "resultAddress");
            return (Criteria) this;
        }

        public Criteria andResultAddressIn(List<String> values) {
            addCriterion("result_address in", values, "resultAddress");
            return (Criteria) this;
        }

        public Criteria andResultAddressNotIn(List<String> values) {
            addCriterion("result_address not in", values, "resultAddress");
            return (Criteria) this;
        }

        public Criteria andResultAddressBetween(String value1, String value2) {
            addCriterion("result_address between", value1, value2, "resultAddress");
            return (Criteria) this;
        }

        public Criteria andResultAddressNotBetween(String value1, String value2) {
            addCriterion("result_address not between", value1, value2, "resultAddress");
            return (Criteria) this;
        }

        public Criteria andAppIntroIsNull() {
            addCriterion("app_intro is null");
            return (Criteria) this;
        }

        public Criteria andAppIntroIsNotNull() {
            addCriterion("app_intro is not null");
            return (Criteria) this;
        }

        public Criteria andAppIntroEqualTo(String value) {
            addCriterion("app_intro =", value, "appIntro");
            return (Criteria) this;
        }

        public Criteria andAppIntroNotEqualTo(String value) {
            addCriterion("app_intro <>", value, "appIntro");
            return (Criteria) this;
        }

        public Criteria andAppIntroGreaterThan(String value) {
            addCriterion("app_intro >", value, "appIntro");
            return (Criteria) this;
        }

        public Criteria andAppIntroGreaterThanOrEqualTo(String value) {
            addCriterion("app_intro >=", value, "appIntro");
            return (Criteria) this;
        }

        public Criteria andAppIntroLessThan(String value) {
            addCriterion("app_intro <", value, "appIntro");
            return (Criteria) this;
        }

        public Criteria andAppIntroLessThanOrEqualTo(String value) {
            addCriterion("app_intro <=", value, "appIntro");
            return (Criteria) this;
        }

        public Criteria andAppIntroLike(String value) {
            addCriterion("app_intro like", value, "appIntro");
            return (Criteria) this;
        }

        public Criteria andAppIntroNotLike(String value) {
            addCriterion("app_intro not like", value, "appIntro");
            return (Criteria) this;
        }

        public Criteria andAppIntroIn(List<String> values) {
            addCriterion("app_intro in", values, "appIntro");
            return (Criteria) this;
        }
        public Criteria andAppIntroNullIn(List<String> values) {
        	String value=toValues(values);
        	String sql=" (app_intro in ("+value+") ";
        	if(values.contains("null")) {
        		sql+=" or app_intro is null ";
    		}
        	sql+=")";
            addCriterion(sql);
            return (Criteria) this;
        }

        public Criteria andAppIntroNotIn(List<String> values) {
            addCriterion("app_intro not in", values, "appIntro");
            return (Criteria) this;
        }

        public Criteria andAppIntroBetween(String value1, String value2) {
            addCriterion("app_intro between", value1, value2, "appIntro");
            return (Criteria) this;
        }

        public Criteria andAppIntroNotBetween(String value1, String value2) {
            addCriterion("app_intro not between", value1, value2, "appIntro");
            return (Criteria) this;
        }

        public Criteria andFileResultIsNull() {
            addCriterion("file_result is null");
            return (Criteria) this;
        }

        public Criteria andFileResultIsNotNull() {
            addCriterion("file_result is not null");
            return (Criteria) this;
        }

        public Criteria andFileResultEqualTo(String value) {
            addCriterion("file_result =", value, "fileResult");
            return (Criteria) this;
        }

        public Criteria andFileResultNotEqualTo(String value) {
            addCriterion("file_result <>", value, "fileResult");
            return (Criteria) this;
        }

        public Criteria andFileResultGreaterThan(String value) {
            addCriterion("file_result >", value, "fileResult");
            return (Criteria) this;
        }

        public Criteria andFileResultGreaterThanOrEqualTo(String value) {
            addCriterion("file_result >=", value, "fileResult");
            return (Criteria) this;
        }

        public Criteria andFileResultLessThan(String value) {
            addCriterion("file_result <", value, "fileResult");
            return (Criteria) this;
        }

        public Criteria andFileResultLessThanOrEqualTo(String value) {
            addCriterion("file_result <=", value, "fileResult");
            return (Criteria) this;
        }

        public Criteria andFileResultLike(String value) {
            addCriterion("file_result like", value, "fileResult");
            return (Criteria) this;
        }

        public Criteria andFileResultNotLike(String value) {
            addCriterion("file_result not like", value, "fileResult");
            return (Criteria) this;
        }

        public Criteria andFileResultIn(List<String> values) {
            addCriterion("file_result in", values, "fileResult");
            return (Criteria) this;
        }

        public Criteria andFileResultNotIn(List<String> values) {
            addCriterion("file_result not in", values, "fileResult");
            return (Criteria) this;
        }

        public Criteria andFileResultBetween(String value1, String value2) {
            addCriterion("file_result between", value1, value2, "fileResult");
            return (Criteria) this;
        }

        public Criteria andFileResultNotBetween(String value1, String value2) {
            addCriterion("file_result not between", value1, value2, "fileResult");
            return (Criteria) this;
        }

        public Criteria andFileResultAddressIsNull() {
            addCriterion("file_result_address is null");
            return (Criteria) this;
        }

        public Criteria andFileResultAddressIsNotNull() {
            addCriterion("file_result_address is not null");
            return (Criteria) this;
        }

        public Criteria andFileResultAddressEqualTo(String value) {
            addCriterion("file_result_address =", value, "fileResultAddress");
            return (Criteria) this;
        }

        public Criteria andFileResultAddressNotEqualTo(String value) {
            addCriterion("file_result_address <>", value, "fileResultAddress");
            return (Criteria) this;
        }

        public Criteria andFileResultAddressGreaterThan(String value) {
            addCriterion("file_result_address >", value, "fileResultAddress");
            return (Criteria) this;
        }

        public Criteria andFileResultAddressGreaterThanOrEqualTo(String value) {
            addCriterion("file_result_address >=", value, "fileResultAddress");
            return (Criteria) this;
        }

        public Criteria andFileResultAddressLessThan(String value) {
            addCriterion("file_result_address <", value, "fileResultAddress");
            return (Criteria) this;
        }

        public Criteria andFileResultAddressLessThanOrEqualTo(String value) {
            addCriterion("file_result_address <=", value, "fileResultAddress");
            return (Criteria) this;
        }

        public Criteria andFileResultAddressLike(String value) {
            addCriterion("file_result_address like", value, "fileResultAddress");
            return (Criteria) this;
        }

        public Criteria andFileResultAddressNotLike(String value) {
            addCriterion("file_result_address not like", value, "fileResultAddress");
            return (Criteria) this;
        }

        public Criteria andFileResultAddressIn(List<String> values) {
            addCriterion("file_result_address in", values, "fileResultAddress");
            return (Criteria) this;
        }

        public Criteria andFileResultAddressNotIn(List<String> values) {
            addCriterion("file_result_address not in", values, "fileResultAddress");
            return (Criteria) this;
        }

        public Criteria andFileResultAddressBetween(String value1, String value2) {
            addCriterion("file_result_address between", value1, value2, "fileResultAddress");
            return (Criteria) this;
        }

        public Criteria andFileResultAddressNotBetween(String value1, String value2) {
            addCriterion("file_result_address not between", value1, value2, "fileResultAddress");
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

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

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

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }
        
        public Criteria andOrSearch(String appName) {
        	addCriterion("(app_name like '%"+appName+"%' or creator like '%"+appName+"%' or "
        			+ "keywords like '%" + appName +"%' or app_intro like '%"+appName+"%')");
        	return (Criteria) this;
        }
        
        public Criteria andFieldNullLike(String field ,String value) {
        	String sql=field+" like '%"+value+"%' ";
        	if(value==null||value.length()<=0) {
        		sql+=" or "+field+" is null ";
        	}
        	addCriterion(sql);
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