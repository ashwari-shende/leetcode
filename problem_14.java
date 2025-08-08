class Solution {
    public String longestCommonPrefix(String[] strs) {
       String prefix = strs[0];
       for(int a = 1; a < strs.length; a++)
       {
        while(!strs[a].startsWith(prefix))
        {
            prefix = prefix.substring(0, prefix.length()-1);
            System.out.println(prefix);
            if(prefix.isEmpty())
            {
                return prefix;
            }
        }

       }
       return prefix;
    }
}