package com.yan.wang.custom.menu.debugging;

import java.util.List;

public class DebuggingFunctions {
	
	public void stringBuildShow (List<String> list) {
		StringBuilder sb1 = new StringBuilder();
		for(String s: list)
		{
			sb1.append(","+s);
		}	
		System.out.println(sb1.toString());
		System.out.println("size: " + list.size());
	}
}
