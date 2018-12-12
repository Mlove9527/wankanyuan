package com.xtkong.controller.common.sqlparser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lenovo on 2018/12/7.
 */
public class SelectItem {

    static class Column
    {
        private int startIndex;
        private int endIndex;
        private String name;

        public Column(String name,int startIndex,int endIndex)
        {
            this.name=name;
            this.startIndex=startIndex;
            this.endIndex=endIndex;
        }

        public int getStartIndex() {
            return startIndex;
        }

        public void setStartIndex(int startIndex) {
            this.startIndex = startIndex;
        }

        public int getEndIndex() {
            return endIndex;
        }

        public void setEndIndex(int endIndex) {
            this.endIndex = endIndex;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString()
        {
            return "列名: "+this.name+", start: "+this.startIndex+", end: "+this.endIndex;
        }
    }

    private List<SelectItem> selectItemList=new ArrayList<>();
    private String originalSQL;

    private String newSQL;
    private String express;
    private String alias;
    private List<Column> columns=new ArrayList<>();

    public static String[] KEY_WORDS=new String[]{
            "case",
            "when",
            "then",
            "else",
            "in",
            "exists",
            "select",
            "from",
            "where",
            "group",
            "by",
            "order",
            "desc",
            "and",
            "or",
            "not",
            "having",
            "distinct"};

    //private Map<String,String> colMapping;

    //as "colAlias"
    private Pattern as1=Pattern.compile("(\\s+)[Aa][Ss](\\s+)\\\"([\\s\\S]+)\\\"$");
    //as colAlias
    private Pattern as2=Pattern.compile("(\\s+)[Aa][Ss](\\s+)([^\\'\\\"\\)\\(\\s]+)$");
    // "colAlias"
    private Pattern space1=Pattern.compile("(\\s+)\\\"([\\s\\S]+)\\\"$");
    // colAlias
    private Pattern space2=Pattern.compile("(\\s+)([^\\'\\\"\\)\\(\\s]+)$");

    private void cutoffAlias(String str)
    {
        Matcher matchAs1 = as1.matcher(str);
        Matcher matchAs2 = as2.matcher(str);
        Matcher matchSpace1 = space1.matcher(str);
        Matcher matchSpace2 = space2.matcher(str);
        if(matchAs1.find() && matchAs1.start()>0)
        {
            express=str.substring(0,matchAs1.start());
            alias=str.substring(matchAs1.start());
        }
        else if(matchAs2.find() && matchAs2.start()>0)
        {
            express=str.substring(0,matchAs2.start());
            alias=str.substring(matchAs2.start());
        }
        else if(matchSpace1.find() && matchSpace1.start()>0)
        {
            express=str.substring(0,matchSpace1.start());
            alias=str.substring(matchSpace1.start());
        }
        else if(matchSpace2.find() && matchSpace2.start()>0)
        {
            express=str.substring(0,matchSpace2.start());
            alias=str.substring(matchSpace2.start());
        }
        //没有别名
        else
        {
            express=str;
        }
    }

    public SelectItem(String str) throws Exception
    {
        this.originalSQL=str!=null?str.trim():str;
        //this.colMapping=colMapping;
        //将表达式和别名分离
        cutoffAlias(this.originalSQL);

        String recentWord="";
        char packStart=0;
        int startIndex=-1;

        //然后搜索表达式中的列名
        for(int i=0;i<express.length();i++)
        {
            char chr=express.charAt(i);
            //包裹结束
            if(isEndPackChar(chr,packStart) && packStart!=0)
            {
                //TODO 单引号要特殊处理
                if(chr=='\'')
                {

                }

                //双引号结束,获取的可能是表名也可能是列名
                if(isSpecialPackChar(chr) && !recentWord.equals("") && !isHas(express,i+1,Arrays.asList(new Character[]{'.','('})))
                {
                    //把双引号也包括进来
                    Column col=new Column(recentWord,startIndex-1,i);
                    columns.add(col);
                    //System.out.println("*******固定列: "+col);
                }

                recentWord="";
                startIndex=-1;
                packStart=0;
                continue;
            }

            //碰到包裹符之前的点号,说明之前的是表名,从现在开始要准备截取列名
            if(packStart==0 && chr=='.' /* && tabOrColName!=null*/)
            {
                if(!recentWord.equals(""))
                {
                    recentWord="";
                }
                startIndex=-1;
                continue;
            }

            //只截取双引号包裹的字符
            //或没有被包裹的元素字符
            if(isSpecialPackChar(packStart) || (isElementChar(chr) && packStart==0))
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
                if(isPackChar(chr) && packStart==0)
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
                    if(!recentWord.equals("") && isReallyColName(recentWord) && !isHas(express,i, Arrays.asList(new Character[]{'.','('})))
                    {
                        Column col=new Column(recentWord,startIndex,i-1);
                        columns.add(col);
                        //System.out.println("-----------列名: "+col);
                    }
                    recentWord="";
                    startIndex=-1;
                    //continue;
                }
            }
        }

        if(!recentWord.equals("") && isReallyColName(recentWord))
        {
            Column col=new Column(recentWord,startIndex,express.length()-1);
            columns.add(col);
            //System.out.println("################列名: "+col);
        }

        //replace();
    }

    public void replace(Map<String,String> colMapping)
    {
        String result="";
        int startIdx=0;
        for(Column col : this.columns)
        {
            result+=express.substring(startIdx,col.startIndex);
            //System.out.println("00000after replace: "+result);
            String newVal=colMapping.get(col.getName());
            result+=(newVal==null?col.getName():newVal);
            //System.out.println("11111after replace: "+result);
            startIdx=col.getEndIndex()+1;
        }

        if(startIdx<express.length())
        {
            result+=express.substring(startIdx);
        }

        newSQL=result+(alias==null?"":alias);
        //System.out.println("22222after replace: "+express);
    }

    public static boolean isHas(String expStr,int startIdx,List<Character> strings)
    {
        for(int i=startIdx; i<expStr.length(); i++)
        {
            char chr=expStr.charAt(i);
            if(isFormatChar(chr))
            {
                continue;
            }
            else if(strings.contains(chr))
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        return false;
    }

    public static boolean isReallyColName(String colName)
    {
        if(colName!=null && !colName.trim().equals(""))
        {
            try
            {
                Double.parseDouble(colName);
            }
            catch(Exception e)
            {
                if(!Arrays.asList(KEY_WORDS).contains(colName.toLowerCase()))
                {
                    return true;
                }

            }

            return false;

        }
        return false;
    }

    public static boolean isSpecialPackChar(char chr)
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
    public static boolean isPackChar(char chr)
    {
        if(chr=='\"' || chr=='\'')
        {
            return true;
        }

        return false;
    }

    /*
    是否是结束包裹符
     */
    public static boolean isEndPackChar(char chr,char packStartChr)
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
    public static boolean isElementChar(char chr)
    {
        if(!isPackChar(chr) && !isFormatChar(chr) && chr!=',' && chr!='(' && chr!=')' && !isOperator(chr))
        {
            return true;
        }
        return false;
    }

    public static boolean isOperator(char chr)
    {
        if(chr=='=' || chr=='>' || chr=='<' || chr=='+' || chr=='-' || chr=='*' || chr=='/' || chr=='%'|| chr=='!'|| chr=='^'|| chr=='&'|| chr=='|')
        {
            return true;
        }
        return false;
    }

    /*
    是否是格式符
     */
    public static boolean isFormatChar(char chr)
    {
        if(chr=='\t' || chr=='\n' || chr=='\r' || chr==' ')
        {
            return true;
        }

        return false;
    }

    public String getOriginalSQL() {
        return originalSQL;
    }

    public String getExpress() {
        return express;
    }

    public String getNewSQL() {
        return newSQL;
    }
}
