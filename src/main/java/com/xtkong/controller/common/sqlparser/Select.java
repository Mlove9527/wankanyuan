package com.xtkong.controller.common.sqlparser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2018/12/7.
 */
public class Select {

    private List<SelectItem> selectItemList=new ArrayList<>();
    private From from;
    private Where where;
    private boolean hasFromKeyword=false;
    private boolean hasWhereKeyword=false;
    private String originalSQL;
    private String newSQL;
    private int fromStartIndex=-1;

    public Select(String str,Map<String,String> tabMapping,Map<String,String> colMapping) throws Exception
    {
        originalSQL=str.trim();

        if(!originalSQL.toLowerCase().startsWith("select "))
        {
            throw new Exception("非法的Select语句!");
        }

        String strToDeal=originalSQL.substring("select ".length());
        String tmpSelectItemStr="";
        String recentWord="";
        char packStart=0;

        for(int i=0;i<strToDeal.length();i++)
        {
            char chr=strToDeal.charAt(i);

            //包裹结束
            if(isEndPackChar(chr,packStart) && packStart!=0)
            {
                //TODO 单引号要特殊处理
                if(chr=='\'')
                {

                }

                tmpSelectItemStr+=chr;
                recentWord="";
                packStart=0;
                continue;
            }

            if(packStart!=0 || isElementChar(chr))
            {
                tmpSelectItemStr+=chr;
                recentWord+=chr;
            }
            else
            {
                if(isSpecialPackChar(chr))
                {
                    tmpSelectItemStr+=chr;
                }
                else if(isPackChar(chr))
                {
                    packStart=chr;
                    tmpSelectItemStr+=chr;
                }
                else if(chr==',')
                {
                    selectItemList.add(new SelectItem(tmpSelectItemStr,colMapping));
                    tmpSelectItemStr="";
                }
                else if( recentWord.toLowerCase().equals("from") ||  recentWord.toLowerCase().equals("where"))
                {
                    if(recentWord.toLowerCase().equals("from"))
                    {
                        hasFromKeyword=true;
                        tmpSelectItemStr=tmpSelectItemStr.substring(0,tmpSelectItemStr.length()-"from".length());
                        selectItemList.add(new SelectItem(tmpSelectItemStr,colMapping));
                        tmpSelectItemStr="";
                    }
                    else if(recentWord.toLowerCase().equals("where"))
                    {
                        hasWhereKeyword=true;
                        tmpSelectItemStr=tmpSelectItemStr.substring(0,tmpSelectItemStr.length()-"where".length());
                        from=new From(tmpSelectItemStr,tabMapping);
                        tmpSelectItemStr="";
                    }
                }
                else
                {
                    tmpSelectItemStr+=chr;
                }
                recentWord="";
            }
        }

        if(!hasFromKeyword)
        {
            //selectItemList.add(new SelectItem(tmpSelectItemStr,colMapping));
            throw new Exception("select语句必须要有from.");
        }
        else if(!hasWhereKeyword)
        {
            from=new From(tmpSelectItemStr,tabMapping);
        }
        else
        {
            where=new Where(tmpSelectItemStr,colMapping);
        }

        replace();
    }

    private void replace() throws Exception
    {
        newSQL="select ";
        boolean catoff=false;
        for(SelectItem selectItem : this.selectItemList)
        {
            newSQL+=selectItem.getNewSQL()+",";
            catoff=true;
        }
        if(!catoff) {
            throw new Exception("select没有item,非法的select语句.");
        }
        newSQL=newSQL.substring(0,newSQL.length()-1);

        fromStartIndex=newSQL.length()+1;
        newSQL+=" from "+from.getNewSQL();
        if(where!=null)
        {
            newSQL+=" where "+where.getNewSQL();
        }
    }

    private boolean isSpecialPackChar(char chr)
    {
        if(chr=='\"')
        {
            return true;
        }

        return false;
    }
    /*
    是否是包裹符
     */
    private boolean isPackChar(char chr)
    {
        if(chr=='\"' || chr=='(' || chr==')' || chr=='\'')
        {
            return true;
        }

        return false;
    }

    /*
    是否是结束包裹符
     */
    private boolean isEndPackChar(char chr,char packStartChr)
    {
        if(chr=='\"' && packStartChr=='\"')
        {
            return true;
        }
        else if(chr==')' && packStartChr=='(')
        {
            return true;
        }
        else if(chr=='\'' && packStartChr=='\'')
        {
            return true;
        }

        return false;
    }

    /*
    是否是元素符
     */
    private boolean isElementChar(char chr)
    {
        if(!isPackChar(chr) && !isFormatChar(chr) && chr!=',')
        {
            return true;
        }
        return false;
    }

    /*
    是否是格式符
     */
    private boolean isFormatChar(char chr)
    {
        if(chr=='\t' || chr=='\n' || chr=='\r' || chr==' ')
        {
            return true;
        }

        return false;
    }

    public List<SelectItem> getSelectItemList() {
        return selectItemList;
    }

    public From getFrom() {
        return from;
    }

    public Where getWhere() {
        return where;
    }

    public String getNewSQL() {
        return newSQL;
    }

    public int getFromStartIndex() {
        return fromStartIndex;
    }
}
