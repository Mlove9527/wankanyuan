package com.xtkong.controller.common.sqlparser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2018/12/7.
 */
public class Where {

    private String originalSQL;

    private String newSQL;

    private Map<String,String> colMapping;

    private List<SelectItem.Column> columns=new ArrayList<>();

    public Where(String str,Map<String,String> colMapping) throws Exception
    {
        this.originalSQL=str!=null?str.trim():str;
        this.colMapping=colMapping;

        String recentWord="";
        char packStart=0;
        int startIndex=-1;

        //然后搜索表达式中的列名
        for(int i=0;i<originalSQL.length();i++)
        {
            char chr=originalSQL.charAt(i);
            //包裹结束
            if(SelectItem.isEndPackChar(chr,packStart) && packStart!=0)
            {
                //TODO 单引号要特殊处理
                if(chr=='\'')
                {

                }

                //双引号结束,获取的可能是表名也可能是列名
                if(SelectItem.isSpecialPackChar(chr) && !recentWord.equals("") && !SelectItem.isHas(originalSQL,i+1,Arrays.asList(new Character[]{'.','('})))
                {
                    //把双引号也包括进来
                    SelectItem.Column col=new SelectItem.Column(recentWord,startIndex-1,i);
                    columns.add(col);
                    //System.out.println("*******固定列: "+col);
                }

                recentWord="";
                startIndex=-1;
                packStart=0;
                continue;
            }

            //碰到包裹符之前的点号,说明之前的是表名,从现在开始要准备截取列名
            if(packStart==0 && chr=='.'/* && tabOrColName!=null*/)
            {
                recentWord="";
                startIndex=-1;
                continue;
            }

            //只截取双引号包裹的字符
            //或没有被包裹的元素字符
            if(SelectItem.isSpecialPackChar(packStart) || (SelectItem.isElementChar(chr) && packStart==0))
            {
                if(startIndex<0)
                {
                    startIndex=i;
                }
                recentWord+=chr;
            }
            //如果是在单引号包裹内
            else if(packStart=='\'')
            {
                continue;
            }
            else
            {
                //包裹符开启
                if(SelectItem.isPackChar(chr) && packStart==0)
                {
                    packStart=chr;
                }
                //表示之前的recentWord不是列名,应该是函数名
                else if(chr=='(')
                {
                    recentWord="";
                    startIndex=-1;
                }
                //如果是格式符,忽略掉
                else// if(isFormatChar(chr))
                {
                    if(!recentWord.equals("") && SelectItem.isReallyColName(recentWord) && !SelectItem.isHas(originalSQL,i,Arrays.asList(new Character[]{'.','('})))
                    {
                        SelectItem.Column col=new SelectItem.Column(recentWord,startIndex,i-1);
                        columns.add(col);
                        //System.out.println("-----------列名: "+col);
                    }
                    recentWord="";
                    startIndex=-1;
                    //continue;
                }
            }
        }

        if(!recentWord.equals("") && SelectItem.isReallyColName(recentWord))
        {
            SelectItem.Column col=new SelectItem.Column(recentWord,startIndex,originalSQL.length()-1);
            columns.add(col);
            //System.out.println("################列名: "+col);
        }

        replace();
    }

    private void replace()
    {
        String result="";
        int startIdx=0;
        for(SelectItem.Column col : this.columns)
        {
            result+=originalSQL.substring(startIdx,col.getStartIndex());
            //System.out.println("00000after replace: "+result);
            String newVal=this.colMapping.get(col.getName());
            result+=(newVal==null?col.getName():newVal);
            //System.out.println("11111after replace: "+result);
            startIdx=col.getEndIndex()+1;
        }

        if(startIdx<originalSQL.length())
        {
            result+=originalSQL.substring(startIdx);
        }

        newSQL=result;
        //originalSQL=result+(alias==null?"":alias);
        //System.out.println("22222after replace: "+express);
    }

    public String getOriginalSQL() {
        return originalSQL;
    }

    public String getNewSQL() {
        return newSQL;
    }
}
