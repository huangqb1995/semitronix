package upload;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class TimeStampTool {
	private SimpleDateFormat sdf = null;
	public TimeStampTool(){}

	public String getTimeRand(){
		StringBuffer buf = new StringBuffer();
		buf.append(this.getTimeStamp());
		Random r = new Random();
		for(int i = 0; i < 3; i++){
			buf.append(r.nextInt(10));
		}
		return buf.toString();
	}

	public String getTimeStamp(){
		this.sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return this.sdf.format(new Date());
	}
}
