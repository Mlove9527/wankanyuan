package com.xtkong.controller.common.sqlparser;

import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lenovo on 2018/12/7.
 */
public class From {

    private String originalSQL;
    private String newSQL;

    private Map<String,String> tabMapping;

    //as "colAlias"
    private Pattern as1=Pattern.compile("(\\s+)[Aa][Ss](\\s+)\\\"([\\s\\S]+)\\\"$");
    //as colAlias
    private Pattern as2=Pattern.compile("(\\s+)[Aa][Ss](\\s+)([^\\'\\\"\\)\\(\\s]+)$");
    // "colAlias"
    private Pattern space1=Pattern.compile("(\\s+)\\\"([\\s\\S]+)\\\"$");
    // colAlias
    private Pattern space2=Pattern.compile("(\\s+)([^\\'\\\"\\)\\(\\s]+)$");

    private String schema;
    private String tableName;
    private String alias;

    private void cutoffAlias(String str)
    {
        Matcher matchAs1 = as1.matcher(str);
        Matcher matchAs2 = as2.matcher(str);
        Matcher matchSpace1 = space1.matcher(str);
        Matcher matchSpace2 = space2.matcher(str);
        if(matchAs1.find() && matchAs1.start()>0)
        {
            tableName=str.substring(0,matchAs1.start());
            alias=str.substring(matchAs1.start());
        }
        else if(matchAs2.find() && matchAs2.start()>0)
        {
            tableName=str.substring(0,matchAs2.start());
            alias=str.substring(matchAs2.start());
        }
        else if(matchSpace1.find() && matchSpace1.start()>0)
        {
            tableName=str.substring(0,matchSpace1.start());
            alias=str.substring(matchSpace1.start());
        }
        else if(matchSpace2.find() && matchSpace2.start()>0)
        {
            tableName=str.substring(0,matchSpace2.start());
            alias=str.substring(matchSpace2.start());
        }
        //没有别名
        else
        {
            tableName=str;
        }
    }

    public From(String str,Map<String,String> tabMapping) throws Exception
    {
        this.originalSQL=str!=null?str.trim():str;
        this.tabMapping=tabMapping;

        cutoffAlias(this.originalSQL);

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
                if(SelectItem.isSpecialPackChar(chr) && !recentWord.equals(""))
                {
                    if(SelectItem.isHas(originalSQL,i+1, Arrays.asList(new Character[]{'.'})))
                    {
                        schema=recentWord;
                    }
                    else
                    {
                        tableName=recentWord;
                    }
                }

                recentWord="";
                startIndex=-1;
                packStart=0;
                continue;
            }

            //碰到包裹符之前的点号,说明之前的是表名,从现在开始要准备截取列名
            if(packStart==0 && chr=='.'/* && tabOrColName!=null*/)
            {
                schema=recentWord;
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
                    if(!recentWord.equals("") && SelectItem.isReallyColName(recentWord))
                    {
                        if(SelectItem.isHas(originalSQL,i+1, Arrays.asList(new Character[]{'.'})))
                        {
                            schema=recentWord;
                        }
                    }
                    recentWord="";
                    startIndex=-1;
                    //continue;
                }
            }
        }

        replace();
    }

    private void replace()
    {

    }

    public String getOriginalSQL() {
        return originalSQL;
    }

    public String getTableName() {
        return tableName;
    }

    @Override
    public String toString()
    {
        return schema+":"+tableName+":"+alias;
    }

    public String getNewSQL() {
        return newSQL;
    }
}
