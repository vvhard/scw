package com.atguigu.scw.manager.bean.example;

import java.util.ArrayList;
import java.util.List;

public class TTokenExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TTokenExample() {
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

        public Criteria andUseridIsNull() {
            addCriterion("userid is null");
            return (Criteria) this;
        }

        public Criteria andUseridIsNotNull() {
            addCriterion("userid is not null");
            return (Criteria) this;
        }

        public Criteria andUseridEqualTo(Integer value) {
            addCriterion("userid =", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotEqualTo(Integer value) {
            addCriterion("userid <>", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridGreaterThan(Integer value) {
            addCriterion("userid >", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridGreaterThanOrEqualTo(Integer value) {
            addCriterion("userid >=", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridLessThan(Integer value) {
            addCriterion("userid <", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridLessThanOrEqualTo(Integer value) {
            addCriterion("userid <=", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridIn(List<Integer> values) {
            addCriterion("userid in", values, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotIn(List<Integer> values) {
            addCriterion("userid not in", values, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridBetween(Integer value1, Integer value2) {
            addCriterion("userid between", value1, value2, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotBetween(Integer value1, Integer value2) {
            addCriterion("userid not between", value1, value2, "userid");
            return (Criteria) this;
        }

        public Criteria andPswTokenIsNull() {
            addCriterion("psw_token is null");
            return (Criteria) this;
        }

        public Criteria andPswTokenIsNotNull() {
            addCriterion("psw_token is not null");
            return (Criteria) this;
        }

        public Criteria andPswTokenEqualTo(String value) {
            addCriterion("psw_token =", value, "pswToken");
            return (Criteria) this;
        }

        public Criteria andPswTokenNotEqualTo(String value) {
            addCriterion("psw_token <>", value, "pswToken");
            return (Criteria) this;
        }

        public Criteria andPswTokenGreaterThan(String value) {
            addCriterion("psw_token >", value, "pswToken");
            return (Criteria) this;
        }

        public Criteria andPswTokenGreaterThanOrEqualTo(String value) {
            addCriterion("psw_token >=", value, "pswToken");
            return (Criteria) this;
        }

        public Criteria andPswTokenLessThan(String value) {
            addCriterion("psw_token <", value, "pswToken");
            return (Criteria) this;
        }

        public Criteria andPswTokenLessThanOrEqualTo(String value) {
            addCriterion("psw_token <=", value, "pswToken");
            return (Criteria) this;
        }

        public Criteria andPswTokenLike(String value) {
            addCriterion("psw_token like", value, "pswToken");
            return (Criteria) this;
        }

        public Criteria andPswTokenNotLike(String value) {
            addCriterion("psw_token not like", value, "pswToken");
            return (Criteria) this;
        }

        public Criteria andPswTokenIn(List<String> values) {
            addCriterion("psw_token in", values, "pswToken");
            return (Criteria) this;
        }

        public Criteria andPswTokenNotIn(List<String> values) {
            addCriterion("psw_token not in", values, "pswToken");
            return (Criteria) this;
        }

        public Criteria andPswTokenBetween(String value1, String value2) {
            addCriterion("psw_token between", value1, value2, "pswToken");
            return (Criteria) this;
        }

        public Criteria andPswTokenNotBetween(String value1, String value2) {
            addCriterion("psw_token not between", value1, value2, "pswToken");
            return (Criteria) this;
        }

        public Criteria andAutoTokenIsNull() {
            addCriterion("auto_token is null");
            return (Criteria) this;
        }

        public Criteria andAutoTokenIsNotNull() {
            addCriterion("auto_token is not null");
            return (Criteria) this;
        }

        public Criteria andAutoTokenEqualTo(String value) {
            addCriterion("auto_token =", value, "autoToken");
            return (Criteria) this;
        }

        public Criteria andAutoTokenNotEqualTo(String value) {
            addCriterion("auto_token <>", value, "autoToken");
            return (Criteria) this;
        }

        public Criteria andAutoTokenGreaterThan(String value) {
            addCriterion("auto_token >", value, "autoToken");
            return (Criteria) this;
        }

        public Criteria andAutoTokenGreaterThanOrEqualTo(String value) {
            addCriterion("auto_token >=", value, "autoToken");
            return (Criteria) this;
        }

        public Criteria andAutoTokenLessThan(String value) {
            addCriterion("auto_token <", value, "autoToken");
            return (Criteria) this;
        }

        public Criteria andAutoTokenLessThanOrEqualTo(String value) {
            addCriterion("auto_token <=", value, "autoToken");
            return (Criteria) this;
        }

        public Criteria andAutoTokenLike(String value) {
            addCriterion("auto_token like", value, "autoToken");
            return (Criteria) this;
        }

        public Criteria andAutoTokenNotLike(String value) {
            addCriterion("auto_token not like", value, "autoToken");
            return (Criteria) this;
        }

        public Criteria andAutoTokenIn(List<String> values) {
            addCriterion("auto_token in", values, "autoToken");
            return (Criteria) this;
        }

        public Criteria andAutoTokenNotIn(List<String> values) {
            addCriterion("auto_token not in", values, "autoToken");
            return (Criteria) this;
        }

        public Criteria andAutoTokenBetween(String value1, String value2) {
            addCriterion("auto_token between", value1, value2, "autoToken");
            return (Criteria) this;
        }

        public Criteria andAutoTokenNotBetween(String value1, String value2) {
            addCriterion("auto_token not between", value1, value2, "autoToken");
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