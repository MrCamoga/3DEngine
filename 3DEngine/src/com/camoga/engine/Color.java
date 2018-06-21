package com.camoga.engine;

public class Color {
	public static float[] RGBtoHSL(int rgb) {
		float r = ((rgb&0xff0000) >> 16)/255.0f;
		float g = ((rgb&0xff00) >> 8)/255.0f;
		float b = (rgb&0xff)/255.0f;
		
		float M = Math.max(Math.max(r, g), b);
		float m = Math.min(Math.min(r, g), b);
		float C = M-m;
		
		float Hp = 0;
		if(M==r) Hp = (g-b)/C % 6;
		else if (M==g) Hp = (b-r)/C + 2;
		else if(M==b) Hp = (r-g)/C + 4;
		
		float H = Hp*60f;
		float L = (M+m)*0.5f;
		float S = L == 1 ? 0:(C/(1-Math.abs(2*L-1)));
		
//		System.out.println(r+","+g+","+b+": " + H +","+S+","+L);
		return new float[] {H,S,L};
	}
	
	public static int HSLtoRGB(float h, float s, float l) {
		float C = (1-Math.abs(2*l-1))*s;
		float Hp = h/60.0f;
		float X = C*(1-Math.abs((Hp%2)-1));
		
		float r = 0, g = 0, b = 0;
		
		if(Hp <= 1) {
			r=C; g=X;
		} else if(Hp <= 2) {
			r=X; g=C;
		} else if(Hp <= 3) {
			g=C; b=X;
		} else if(Hp <= 4) {
			g=X; b=C;
		} else if(Hp <= 5) {
			r=X; b=C;
		} else if(Hp < 6) {
			r=C; b=X;
		}
		float m = (l - 0.5f*C);
//		System.out.println((r+m)*0xff+","+(g+m)*0xff+","+(b+m)*0xff);
		
		
		return (0xff << 24) | ((int)((r+m)*0xff)<<16) | ((int)((g+m)*0xff)<<8) | (int)((b+m)*0xff);
	}
	
	public static void main(String[] args) {
		float[] hsl = RGBtoHSL(0xffa0a424);
		System.out.println(HSLtoRGB(hsl[0], hsl[1], hsl[2]));
	}
}
