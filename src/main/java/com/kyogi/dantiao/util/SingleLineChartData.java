package com.kyogi.dantiao.util;

import com.kyogi.dantiao.DantiaoMod;

import java.io.IOException;

public class SingleLineChartData {
	private int gameTimes; //比赛次数
    private int typeCommandTimes; //指令使用次数

	public SingleLineChartData() {
		gameTimes = 0;
		typeCommandTimes = 0;
	}

	public void addGameTimes() {
		gameTimes++;
	}
	
	public void addTypeCommandTimes() {
		typeCommandTimes++;
	}

	public int uploadGameTimes() {
		int gameTimes = this.gameTimes;
		this.gameTimes = 0;
		return gameTimes;
	}
	
	public int uploadTypeCommandTimes() {
		int typeCommandTimes = this.typeCommandTimes;
		this.typeCommandTimes = 0;
		return typeCommandTimes;
	}
	
	public int uploadGoodNumber() throws IOException {
		return DantiaoMod.getCacheHandler().getShop().size();
	}
	
	public int uploadArenaNumber() {
		return DantiaoMod.getArenaManager().size();
	}
}
