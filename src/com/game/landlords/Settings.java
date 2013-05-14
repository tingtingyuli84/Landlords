package com.game.landlords;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.game.landlords.core.FileIO;

/*存档格式
 * 声音控制 默认false
 * 当前积分 默认1000
 * 等级/城池 默认 30
 * 经验值 默认 0
 * U的积分 默认1000
 * U的城池 默认30
 * H的积分 默认1000
 * H的城池 默认 30
 * 用户势力：0未选择 1蜀国  2吴国 3魏国
 * 势力重划：0未重划  4蜀归魏 5魏归蜀 6蜀归吴 7吴归属 8唔归魏 9魏归吴
 */
public class Settings {
    private static boolean soundEnabled = true;
    public static int score = 150;
    public static int level = 1;
    public static int exp = 0;
    public static int scoreU = 1300;
//    public static int levelU = 30;
    public static int scoreH = 1400;
//    public static int levelH = 30;
    public static int userID = 0;
    public static int compID = 0;
    public static int heroNum = 0;
//    public static String name = "";
    public static boolean isWithCardsIdenticalAllowed = false;
    public static final int LEVEL_UPGRADE_EXP = 100;

    public static void load(FileIO files) {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(
                    files.readFile(".landlords")));
            soundEnabled = Boolean.parseBoolean(in.readLine());
            score = Integer.parseInt(in.readLine()); 
            System.out.println("score " + score);
            level = Integer.parseInt(in.readLine());
            exp = Integer.parseInt(in.readLine());
//            name = String.valueOf(in.readLine());
//            scoreU = Integer.parseInt(in.readLine());
//            levelU = Integer.parseInt(in.readLine());
//            scoreH = Integer.parseInt(in.readLine());
//            levelH = Integer.parseInt(in.readLine());
            userID = Integer.parseInt(in.readLine());
            compID = Integer.parseInt(in.readLine());
            heroNum = Integer.parseInt(in.readLine());
        } catch (IOException e) {
            // :( It's ok we have defaults
        } catch (NumberFormatException e) {
            // :/ It's ok, defaults save our day
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
            }
        }
    }

    public static void save(FileIO files) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    files.writeFile(".landlords")));
            out.write(Boolean.toString(soundEnabled));
            out.write("\n");
            out.write(Integer.toString(score));
            out.write("\n");
            out.write(Integer.toString(level));
            out.write("\n");
            out.write(Integer.toString(exp));
            out.write("\n");
            System.out.println("score " + score);
//            out.write(name);
//            out.write("\n");
//            out.write(Integer.toString(scoreU));
//            out.write("\n");
//            out.write(Integer.toString(levelU));
//            out.write("\n");
//            out.write(Integer.toString(scoreH));
//            out.write("\n");
//            out.write(Integer.toString(levelH));
//            out.write("\n");
            out.write(Integer.toString(userID));
            out.write("\n");
            out.write(Integer.toString(compID));
            out.write("\n");
            out.write(Integer.toString(heroNum));
            out.write("\n");
        } catch (IOException e) {
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
            }
        }
    }

    public static void addLevel(int levelT)
    {
//    	levelT++;
    }
    
    public static void setScore(int n){
    	score = n;
    }
    
    public static void plusLevel(int levelT)
    {
    	if(levelT >= 0){
    		levelT--;
    	}
    	else {
    		levelT = 0;
    	}
    }
    
    public static void addHero(int n){
    	heroNum += n;
    	if(heroNum > 90)
    		heroNum = 90;
    }
    
    public static void setHero(int n){
    	heroNum = n;
    }
    
    public static void setExp(int newExp)
    {
    	exp = newExp;
    }
    
    public static void addExp()
    {
    	exp++;
    }
    
    public static int getScore()
    {
    	return score;
    }
    
    public static int getCity()
    {
    	return level;
    }

    public static void addCitys(int n)
    {
    	level += n;
    	if(level < 1){
    		level = 1;
    	}
    }
    
    public static void setCitys(int n)
    {
    	level = n;
    }
    
    public static void setUserId(int n)
    {
    	userID = n;
    }
    
    public static int getUserId()
    {
    	return userID;
    }
    
    public static int getHerosNum()
    {
    	return heroNum;
    }
    
	public static void addScore(int baseScore) {
		score += baseScore;
//		scoreU += baseScoreU;
//		scoreH += baseScoreH;
		if(score < 0)
			score = 0;
//		if(scoreU < 0)
//			scoreU = 0;
//		if(scoreH < 0)
//			scoreH = 0;
	}
	
	public static void toggleSound()
	{
		if (soundEnabled)
		{
			soundEnabled = false;
		}
		else
		{
			soundEnabled = true;
			Assets.playSound(Assets.sMusicOn);
		}
	}
	
	public static boolean getSoundEnabled()
	{
		return soundEnabled;
	}
    
//    public static void setName(String newName)
//    {
//    	name = newName;
//    }
}
