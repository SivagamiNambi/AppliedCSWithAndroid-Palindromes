package com.google.engedu.palindromes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Range;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private HashMap<Range, PalindromeGroup> findings = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public boolean onFindPalindromes(View view) {
        findings.clear();
        EditText editText = (EditText) findViewById(R.id.editText);
        TextView textView = (TextView) findViewById(R.id.textView);
        String text = editText.getText().toString();
        text = text.replaceAll(" ", "");
        text = text.replaceAll("'", "");
        text=" "+text;
        char[] textAsChars = text.toCharArray();
        if (isPalindrome(textAsChars, 0, text.length())) {
          textView.setText(text + " is already a palindrome!");
        } else {
           String palindromes = breakIntoPalindromes(text.toCharArray(), 0, text.length());
            textView.setText(palindromes.toString());
        }
        return true;
    }

    private boolean isPalindrome(char[] text, int start, int end) {

        int i,j;

        for( i=start, j=end-1;i<=j;i++,j--)
            if(text[i]!=text[j]) return false;

        return true;
    }

    private String breakIntoPalindromes(char[] text, int start, int end) {
        PalindromeGroup bestGroup = null;
        String ans="";


        boolean dp[][]=new boolean[end][end];
        int c[] =new int[end];
        int pos[] =new int[end];
        //pos[0]=-1;
        int i,j,k,L;
        for(i=0;i<end;++i)
        {
            dp[i][i]=true;
        }

        for(L=2;L<=end;++L)
        {
            for(i=0;i<end-L+1;++i)
            {
                j=i+L-1;

                if(L==2)
                    dp[i][j]=(text[i]==text[j]);
                else
                    dp[i][j]=(text[i]==text[j])&&dp[i+1][j-1];
            }
        }

        for(i=0;i<end;i++){
            if(dp[0][i]==true)
                c[i]=-1;
            else
            {
                c[i]=10000;
                for(j=0;j<i;j++)
                {
                    if(dp[j+1][i]==true && 1+c[j]<c[i])
                    {  c[i]=1+c[j];
                        pos[i]=j;}

                }
            }
        }
        Log.i("split",c[end-1]+" ");
        for(i=0;i<end;++i)
            Log.i(i+" ",pos[i]+"");
        int index=end-1;
       // if(pos[0]==0) pos[0]=-1;

        do{
            ans+=toString(text,pos[index]+1,index);
            ans+=" ";
            if(index==0)break;
            index=pos[index];

        }while(true);

        char [] temp=ans.toCharArray();
        char t;
        for( i=0,j=ans.length()-1;i<=j;i++,j--)
        { t=temp[i];
            temp[i]=temp[j];
            temp[j]=t;

        }
        ans="";
        for(i=0;i<temp.length;++i)
            ans+=temp[i];
        return ans;
    }

    public String toString(char [] text, int start, int end)
    {
        String s="";
        for(int i=start;i<=end;++i)
            s+=text[i];
        return s;
    }
}
