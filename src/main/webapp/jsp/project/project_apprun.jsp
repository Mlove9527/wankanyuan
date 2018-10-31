<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>Document</title>
</head>
<link rel="stylesheet" type="text/css" href="/wankangyuan/static/css/project1.css" />
<script type="text/javascript" src="/wankangyuan/jsp/project/js/project1.js"></script>
<script type="text/javascript">
    window.onload=function(){
        //project0();
        // project1();
        // pro_detail();
        // pro_deappendxq();
        //pro_appendre();
    }
</script>
<body>
    <div class="Box">
        <div class="box">
    
            <div class="">
                <div class="inportT">
                    <div class="inportTt">重新选择格式数据</div>
                    <div class="inportTx"></div>
                </div>
                <div class="proreKC">
                    <div class="proreT">
                    	<c:forEach items="${source.sourceFields}" var="sourceFieldTemp">
							<div class="proreTli datali">${sourceFieldTemp.csf_name}</div>
						</c:forEach>
                    </div>
                    <div class="proreM">
                    	
                    	<c:forEach items="${sourceDatas}" var="sourceData">
                    		<div class="proreMz">
	                            <div class="proreMzc">
	                            	<c:forEach items="${sourceData}" var="sourceDataField" varStatus="status">
	                            		<c:if test="${status.index!=0}">
											<div class="proreMli datali">${sourceDataField}</div>
										</c:if>
	                            	</c:forEach>
	                            </div>
	                            <div class="PJliB">
	                                <div class="PJliB1">
	                                    <div class="PJliB1L">
	                                        <div class="PJliB1Lt">临床数据</div>
	                                    </div>
	                                    <div class="PJliBR">
	                                        <div class="PJliB2">
	                                            <div class="PJliB2L">
	                                                <div class="fuxuanK">
	                                                    <input type="checkbox" class="input_check" id="check1_1">
	                                                    <label for="check1_1"></label>
	                                                </div>
	                                                <div class="PJliB2Lt">CT</div>
	                                            </div>
	                                            <div class="PJliBR">
	                                                <div class="PJliB2">
	                                                    <div class="PJliB2L">
	                                                        <div class="fuxuanK">
	                                                            <input type="checkbox" class="input_check" id="check1_2">
	                                                            <label for="check1_2"></label>
	                                                        </div>
	                                                        <div class="PJliB2Lt">CT1</div>
	                                                    </div>
	                                                </div>
	                                                <div class="PJliB2">
	                                                    <div class="PJliB2L">
	                                                        <div class="fuxuanK">
	                                                            <input type="checkbox" class="input_check" id="check1_3">
	                                                            <label for="check1_3"></label>
	                                                        </div>
	                                                        <div class="PJliB2Lt">CT2</div>
	                                                    </div>
	                                                </div>
	                                                <div class="PJliB2">
	                                                    <div class="PJliB2L">
	                                                        <div class="fuxuanK">
	                                                            <input type="checkbox" class="input_check" id="check1_4">
	                                                            <label for="check1_4"></label>
	                                                        </div>
	                                                        <div class="PJliB2Lt">CT3</div>
	                                                    </div>
	                                                </div>
	                                            </div>
	                                        </div>
	                                        <div class="PJliB2">
	                                            <div class="PJliB2L">
	                                                <div class="fuxuanK">
	                                                    <input type="checkbox" class="input_check" id="check1_5">
	                                                    <label for="check1_5"></label>
	                                                </div>
	                                                <div class="PJliB2Lt">XG</div>
	                                            </div>
	                                            <div class="PJliBR">
	                                                <div class="PJliB2">
	                                                    <div class="PJliB2L">
	                                                        <div class="fuxuanK">
	                                                            <input type="checkbox" class="input_check" id="check1_6">
	                                                            <label for="check1_6"></label>
	                                                        </div>
	                                                        <div class="PJliB2Lt">XG1</div>
	                                                    </div>
	                                                </div>
	                                                <div class="PJliB2">
	                                                    <div class="PJliB2L">
	                                                        <div class="fuxuanK">
	                                                            <input type="checkbox" class="input_check" id="check1_7">
	                                                            <label for="check1_7"></label>
	                                                        </div>
	                                                        <div class="PJliB2Lt">XG2</div>
	                                                    </div>
	                                                </div>
	                                                <div class="PJliB2">
	                                                    <div class="PJliB2L">
	                                                        <div class="fuxuanK">
	                                                            <input type="checkbox" class="input_check" id="check1_8">
	                                                            <label for="check1_8"></label>
	                                                        </div>
	                                                        <div class="PJliB2Lt">XG3</div>
	                                                    </div>
	                                                </div>
	                                            </div>
	                                        </div>
	                                    </div>
	                                </div>
	                            </div>
	                        </div>
                    		
                    	</c:forEach>

                    </div>
                </div>
                <div class="proreB">
	                <input type="button" class="proreb proreb1" value="提交" />
	                <input type="button" class="proreb proreb2" value="关闭" />
                </div>
            </div>
            
            

            
        </div>
    </div>
</body>
</html>