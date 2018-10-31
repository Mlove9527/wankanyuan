package com.liutianjun.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FriendsQuery {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer pageNo = 1;

    protected Integer startRow;

    protected Integer pageSize = 10;

    protected String fields;

    public FriendsQuery() {
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

        public Criteria andFriendIdIsNull() {
            addCriterion("friend_id is null");
            return (Criteria) this;
        }

        public Criteria andFriendIdIsNotNull() {
            addCriterion("friend_id is not null");
            return (Criteria) this;
        }

        public Criteria andFriendIdEqualTo(Integer value) {
            addCriterion("friend_id =", value, "friendId");
            return (Criteria) this;
        }

        public Criteria andFriendIdNotEqualTo(Integer value) {
            addCriterion("friend_id <>", value, "friendId");
            return (Criteria) this;
        }

        public Criteria andFriendIdGreaterThan(Integer value) {
            addCriterion("friend_id >", value, "friendId");
            return (Criteria) this;
        }

        public Criteria andFriendIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("friend_id >=", value, "friendId");
            return (Criteria) this;
        }

        public Criteria andFriendIdLessThan(Integer value) {
            addCriterion("friend_id <", value, "friendId");
            return (Criteria) this;
        }

        public Criteria andFriendIdLessThanOrEqualTo(Integer value) {
            addCriterion("friend_id <=", value, "friendId");
            return (Criteria) this;
        }

        public Criteria andFriendIdIn(List<Integer> values) {
            addCriterion("friend_id in", values, "friendId");
            return (Criteria) this;
        }

        public Criteria andFriendIdNotIn(List<Integer> values) {
            addCriterion("friend_id not in", values, "friendId");
            return (Criteria) this;
        }

        public Criteria andFriendIdBetween(Integer value1, Integer value2) {
            addCriterion("friend_id between", value1, value2, "friendId");
            return (Criteria) this;
        }

        public Criteria andFriendIdNotBetween(Integer value1, Integer value2) {
            addCriterion("friend_id not between", value1, value2, "friendId");
            return (Criteria) this;
        }

        public Criteria andFriendOrgIdIsNull() {
            addCriterion("friend_org_id is null");
            return (Criteria) this;
        }

        public Criteria andFriendOrgIdIsNotNull() {
            addCriterion("friend_org_id is not null");
            return (Criteria) this;
        }

        public Criteria andFriendOrgIdEqualTo(Integer value) {
            addCriterion("friend_org_id =", value, "friendOrgId");
            return (Criteria) this;
        }

        public Criteria andFriendOrgIdNotEqualTo(Integer value) {
            addCriterion("friend_org_id <>", value, "friendOrgId");
            return (Criteria) this;
        }

        public Criteria andFriendOrgIdGreaterThan(Integer value) {
            addCriterion("friend_org_id >", value, "friendOrgId");
            return (Criteria) this;
        }

        public Criteria andFriendOrgIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("friend_org_id >=", value, "friendOrgId");
            return (Criteria) this;
        }

        public Criteria andFriendOrgIdLessThan(Integer value) {
            addCriterion("friend_org_id <", value, "friendOrgId");
            return (Criteria) this;
        }

        public Criteria andFriendOrgIdLessThanOrEqualTo(Integer value) {
            addCriterion("friend_org_id <=", value, "friendOrgId");
            return (Criteria) this;
        }

        public Criteria andFriendOrgIdIn(List<Integer> values) {
            addCriterion("friend_org_id in", values, "friendOrgId");
            return (Criteria) this;
        }

        public Criteria andFriendOrgIdNotIn(List<Integer> values) {
            addCriterion("friend_org_id not in", values, "friendOrgId");
            return (Criteria) this;
        }

        public Criteria andFriendOrgIdBetween(Integer value1, Integer value2) {
            addCriterion("friend_org_id between", value1, value2, "friendOrgId");
            return (Criteria) this;
        }

        public Criteria andFriendOrgIdNotBetween(Integer value1, Integer value2) {
            addCriterion("friend_org_id not between", value1, value2, "friendOrgId");
            return (Criteria) this;
        }

        public Criteria andFriendNameIsNull() {
            addCriterion("friend_name is null");
            return (Criteria) this;
        }

        public Criteria andFriendNameIsNotNull() {
            addCriterion("friend_name is not null");
            return (Criteria) this;
        }

        public Criteria andFriendNameEqualTo(String value) {
            addCriterion("friend_name =", value, "friendName");
            return (Criteria) this;
        }

        public Criteria andFriendNameNotEqualTo(String value) {
            addCriterion("friend_name <>", value, "friendName");
            return (Criteria) this;
        }

        public Criteria andFriendNameGreaterThan(String value) {
            addCriterion("friend_name >", value, "friendName");
            return (Criteria) this;
        }

        public Criteria andFriendNameGreaterThanOrEqualTo(String value) {
            addCriterion("friend_name >=", value, "friendName");
            return (Criteria) this;
        }

        public Criteria andFriendNameLessThan(String value) {
            addCriterion("friend_name <", value, "friendName");
            return (Criteria) this;
        }

        public Criteria andFriendNameLessThanOrEqualTo(String value) {
            addCriterion("friend_name <=", value, "friendName");
            return (Criteria) this;
        }

        public Criteria andFriendNameLike(String value) {
            addCriterion("friend_name like", value, "friendName");
            return (Criteria) this;
        }

        public Criteria andFriendNameNotLike(String value) {
            addCriterion("friend_name not like", value, "friendName");
            return (Criteria) this;
        }

        public Criteria andFriendNameIn(List<String> values) {
            addCriterion("friend_name in", values, "friendName");
            return (Criteria) this;
        }

        public Criteria andFriendNameNotIn(List<String> values) {
            addCriterion("friend_name not in", values, "friendName");
            return (Criteria) this;
        }

        public Criteria andFriendNameBetween(String value1, String value2) {
            addCriterion("friend_name between", value1, value2, "friendName");
            return (Criteria) this;
        }

        public Criteria andFriendNameNotBetween(String value1, String value2) {
            addCriterion("friend_name not between", value1, value2, "friendName");
            return (Criteria) this;
        }

        public Criteria andFriendRolenameIsNull() {
            addCriterion("friend_rolename is null");
            return (Criteria) this;
        }

        public Criteria andFriendRolenameIsNotNull() {
            addCriterion("friend_rolename is not null");
            return (Criteria) this;
        }

        public Criteria andFriendRolenameEqualTo(String value) {
            addCriterion("friend_rolename =", value, "friendRolename");
            return (Criteria) this;
        }

        public Criteria andFriendRolenameNotEqualTo(String value) {
            addCriterion("friend_rolename <>", value, "friendRolename");
            return (Criteria) this;
        }

        public Criteria andFriendRolenameGreaterThan(String value) {
            addCriterion("friend_rolename >", value, "friendRolename");
            return (Criteria) this;
        }

        public Criteria andFriendRolenameGreaterThanOrEqualTo(String value) {
            addCriterion("friend_rolename >=", value, "friendRolename");
            return (Criteria) this;
        }

        public Criteria andFriendRolenameLessThan(String value) {
            addCriterion("friend_rolename <", value, "friendRolename");
            return (Criteria) this;
        }

        public Criteria andFriendRolenameLessThanOrEqualTo(String value) {
            addCriterion("friend_rolename <=", value, "friendRolename");
            return (Criteria) this;
        }

        public Criteria andFriendRolenameLike(String value) {
            addCriterion("friend_rolename like", value, "friendRolename");
            return (Criteria) this;
        }

        public Criteria andFriendRolenameNotLike(String value) {
            addCriterion("friend_rolename not like", value, "friendRolename");
            return (Criteria) this;
        }

        public Criteria andFriendRolenameIn(List<String> values) {
            addCriterion("friend_rolename in", values, "friendRolename");
            return (Criteria) this;
        }

        public Criteria andFriendRolenameNotIn(List<String> values) {
            addCriterion("friend_rolename not in", values, "friendRolename");
            return (Criteria) this;
        }

        public Criteria andFriendRolenameBetween(String value1, String value2) {
            addCriterion("friend_rolename between", value1, value2, "friendRolename");
            return (Criteria) this;
        }

        public Criteria andFriendRolenameNotBetween(String value1, String value2) {
            addCriterion("friend_rolename not between", value1, value2, "friendRolename");
            return (Criteria) this;
        }

        public Criteria andFriendHeadimgIsNull() {
            addCriterion("friend_headimg is null");
            return (Criteria) this;
        }

        public Criteria andFriendHeadimgIsNotNull() {
            addCriterion("friend_headimg is not null");
            return (Criteria) this;
        }

        public Criteria andFriendHeadimgEqualTo(String value) {
            addCriterion("friend_headimg =", value, "friendHeadimg");
            return (Criteria) this;
        }

        public Criteria andFriendHeadimgNotEqualTo(String value) {
            addCriterion("friend_headimg <>", value, "friendHeadimg");
            return (Criteria) this;
        }

        public Criteria andFriendHeadimgGreaterThan(String value) {
            addCriterion("friend_headimg >", value, "friendHeadimg");
            return (Criteria) this;
        }

        public Criteria andFriendHeadimgGreaterThanOrEqualTo(String value) {
            addCriterion("friend_headimg >=", value, "friendHeadimg");
            return (Criteria) this;
        }

        public Criteria andFriendHeadimgLessThan(String value) {
            addCriterion("friend_headimg <", value, "friendHeadimg");
            return (Criteria) this;
        }

        public Criteria andFriendHeadimgLessThanOrEqualTo(String value) {
            addCriterion("friend_headimg <=", value, "friendHeadimg");
            return (Criteria) this;
        }

        public Criteria andFriendHeadimgLike(String value) {
            addCriterion("friend_headimg like", value, "friendHeadimg");
            return (Criteria) this;
        }

        public Criteria andFriendHeadimgNotLike(String value) {
            addCriterion("friend_headimg not like", value, "friendHeadimg");
            return (Criteria) this;
        }

        public Criteria andFriendHeadimgIn(List<String> values) {
            addCriterion("friend_headimg in", values, "friendHeadimg");
            return (Criteria) this;
        }

        public Criteria andFriendHeadimgNotIn(List<String> values) {
            addCriterion("friend_headimg not in", values, "friendHeadimg");
            return (Criteria) this;
        }

        public Criteria andFriendHeadimgBetween(String value1, String value2) {
            addCriterion("friend_headimg between", value1, value2, "friendHeadimg");
            return (Criteria) this;
        }

        public Criteria andFriendHeadimgNotBetween(String value1, String value2) {
            addCriterion("friend_headimg not between", value1, value2, "friendHeadimg");
            return (Criteria) this;
        }

        public Criteria andFriendEmailIsNull() {
            addCriterion("friend_email is null");
            return (Criteria) this;
        }

        public Criteria andFriendEmailIsNotNull() {
            addCriterion("friend_email is not null");
            return (Criteria) this;
        }

        public Criteria andFriendEmailEqualTo(String value) {
            addCriterion("friend_email =", value, "friendEmail");
            return (Criteria) this;
        }

        public Criteria andFriendEmailNotEqualTo(String value) {
            addCriterion("friend_email <>", value, "friendEmail");
            return (Criteria) this;
        }

        public Criteria andFriendEmailGreaterThan(String value) {
            addCriterion("friend_email >", value, "friendEmail");
            return (Criteria) this;
        }

        public Criteria andFriendEmailGreaterThanOrEqualTo(String value) {
            addCriterion("friend_email >=", value, "friendEmail");
            return (Criteria) this;
        }

        public Criteria andFriendEmailLessThan(String value) {
            addCriterion("friend_email <", value, "friendEmail");
            return (Criteria) this;
        }

        public Criteria andFriendEmailLessThanOrEqualTo(String value) {
            addCriterion("friend_email <=", value, "friendEmail");
            return (Criteria) this;
        }

        public Criteria andFriendEmailLike(String value) {
            addCriterion("friend_email like", value, "friendEmail");
            return (Criteria) this;
        }

        public Criteria andFriendEmailNotLike(String value) {
            addCriterion("friend_email not like", value, "friendEmail");
            return (Criteria) this;
        }

        public Criteria andFriendEmailIn(List<String> values) {
            addCriterion("friend_email in", values, "friendEmail");
            return (Criteria) this;
        }

        public Criteria andFriendEmailNotIn(List<String> values) {
            addCriterion("friend_email not in", values, "friendEmail");
            return (Criteria) this;
        }

        public Criteria andFriendEmailBetween(String value1, String value2) {
            addCriterion("friend_email between", value1, value2, "friendEmail");
            return (Criteria) this;
        }

        public Criteria andFriendEmailNotBetween(String value1, String value2) {
            addCriterion("friend_email not between", value1, value2, "friendEmail");
            return (Criteria) this;
        }

        public Criteria andFriendProfileIsNull() {
            addCriterion("friend_profile is null");
            return (Criteria) this;
        }

        public Criteria andFriendProfileIsNotNull() {
            addCriterion("friend_profile is not null");
            return (Criteria) this;
        }

        public Criteria andFriendProfileEqualTo(String value) {
            addCriterion("friend_profile =", value, "friendProfile");
            return (Criteria) this;
        }

        public Criteria andFriendProfileNotEqualTo(String value) {
            addCriterion("friend_profile <>", value, "friendProfile");
            return (Criteria) this;
        }

        public Criteria andFriendProfileGreaterThan(String value) {
            addCriterion("friend_profile >", value, "friendProfile");
            return (Criteria) this;
        }

        public Criteria andFriendProfileGreaterThanOrEqualTo(String value) {
            addCriterion("friend_profile >=", value, "friendProfile");
            return (Criteria) this;
        }

        public Criteria andFriendProfileLessThan(String value) {
            addCriterion("friend_profile <", value, "friendProfile");
            return (Criteria) this;
        }

        public Criteria andFriendProfileLessThanOrEqualTo(String value) {
            addCriterion("friend_profile <=", value, "friendProfile");
            return (Criteria) this;
        }

        public Criteria andFriendProfileLike(String value) {
            addCriterion("friend_profile like", value, "friendProfile");
            return (Criteria) this;
        }

        public Criteria andFriendProfileNotLike(String value) {
            addCriterion("friend_profile not like", value, "friendProfile");
            return (Criteria) this;
        }

        public Criteria andFriendProfileIn(List<String> values) {
            addCriterion("friend_profile in", values, "friendProfile");
            return (Criteria) this;
        }

        public Criteria andFriendProfileNotIn(List<String> values) {
            addCriterion("friend_profile not in", values, "friendProfile");
            return (Criteria) this;
        }

        public Criteria andFriendProfileBetween(String value1, String value2) {
            addCriterion("friend_profile between", value1, value2, "friendProfile");
            return (Criteria) this;
        }

        public Criteria andFriendProfileNotBetween(String value1, String value2) {
            addCriterion("friend_profile not between", value1, value2, "friendProfile");
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