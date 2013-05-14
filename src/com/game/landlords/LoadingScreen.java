package com.game.landlords;
//
import com.game.landlords.core.Game;
import com.game.landlords.core.Graphics;
import com.game.landlords.core.Pixmap;
import com.game.landlords.core.Screen;
import com.game.landlords.core.Graphics.PixmapFormat;

public class LoadingScreen extends Screen {
	
	private int loadingIndex;
	private float tickTime = 0;
	private static final float TICK_INITIAL = 0.1f;
    private static float tick = TICK_INITIAL;
	
    public LoadingScreen(Game game) {
        super(game);
        screenType = Screen.LOADING;
    }

    @Override
    public void update(float deltaTime) 
    {
//    	tickTime += deltaTime;
//    	while (tickTime > tick)
//    	{
//    		tickTime -= tick;
//    		loadingIndex++;
//    		if (loadingIndex >= 4)
//    		{
//    			loadingIndex = 0;
//    		}
//    	}
        Graphics g = game.getGraphics();
        Assets.backgroundGame = g.newPixmap("background_game.png", PixmapFormat.RGB565);
        Assets.backgroundStart = g.newPixmap("background_start.png", PixmapFormat.RGB565);
        
        Assets.house = g.newPixmap("house.png", PixmapFormat.ARGB4444);
        Assets.houseS2 = g.newPixmap("houseS_2.png", PixmapFormat.ARGB4444);
        Assets.houseS10 = g.newPixmap("houseS_10.png", PixmapFormat.ARGB4444);
        Assets.houseS50 = g.newPixmap("houseS_50.png", PixmapFormat.ARGB4444);
        Assets.houseS500 = g.newPixmap("houseS_500.png", PixmapFormat.ARGB4444);
        Assets.houseS5000 = g.newPixmap("houseS_5000.png", PixmapFormat.ARGB4444);
        Assets.houseS10000 = g.newPixmap("houseS_10000.png", PixmapFormat.ARGB4444);
        Assets.noPermission = g.newPixmap("noPermission.png", PixmapFormat.ARGB4444);
        
        Assets.dialogLeft = g.newPixmap("dialog_down.png", PixmapFormat.ARGB4444);
        Assets.dialogRight = g.newPixmap("dialog_right.png", PixmapFormat.ARGB4444);
        
        Assets.dialogSPass = g.newPixmap("dialogS_pass.png", PixmapFormat.ARGB4444);
        Assets.dialogSFold = g.newPixmap("dialogS_fold.png", PixmapFormat.ARGB4444);
        Assets.dialogSOne = g.newPixmap("dialogS_one.png", PixmapFormat.ARGB4444);
        Assets.dialogSTwo = g.newPixmap("dialogS_two.png", PixmapFormat.ARGB4444);
        Assets.dialogSThree = g.newPixmap("dialogS_three.png", PixmapFormat.ARGB4444);
        
        Assets.banner = g.newPixmap("banner.png", PixmapFormat.ARGB4444);
        Assets.bigBanner = g.newPixmap("big_banner.png", PixmapFormat.ARGB4444);
        
        Assets.buttonLPressed = g.newPixmap("button_l_pressed.png", PixmapFormat.ARGB4444);
        Assets.buttonL = g.newPixmap("button_l.png", PixmapFormat.ARGB4444);
        Assets.buttonPressed = g.newPixmap("button_pressed.png", PixmapFormat.ARGB4444);
        Assets.button = g.newPixmap("button.png", PixmapFormat.ARGB4444);
        
        Assets.buttonSHelp = g.newPixmap("buttonS_help.png", PixmapFormat.ARGB4444);
        Assets.buttonSHightscore = g.newPixmap("buttonS_highscore.png", PixmapFormat.ARGB4444);
        Assets.buttonSSetup = g.newPixmap("buttonS_setup.png", PixmapFormat.ARGB4444);
        Assets.buttonSStart = g.newPixmap("buttonS_start.png", PixmapFormat.ARGB4444);
        Assets.buttonSFold = g.newPixmap("buttonS_fold.png", PixmapFormat.ARGB4444);
        Assets.buttonSOne = g.newPixmap("buttonS_one.png", PixmapFormat.ARGB4444);
        Assets.buttonSTwo = g.newPixmap("buttonS_two.png", PixmapFormat.ARGB4444);
        Assets.buttonSThree = g.newPixmap("buttonS_three.png", PixmapFormat.ARGB4444);
        Assets.buttonSPass = g.newPixmap("buttonS_pass.png", PixmapFormat.ARGB4444);
        Assets.buttonSReChoose = g.newPixmap("buttonS_rechoose.png", PixmapFormat.ARGB4444);
        Assets.buttonSTip = g.newPixmap("buttonS_tip.png", PixmapFormat.ARGB4444);
        Assets.buttonSCall = g.newPixmap("buttonS_call.png", PixmapFormat.ARGB4444);
        Assets.buttonChar = g.newPixmap("button.png", PixmapFormat.ARGB4444);
        /*
         * menu����
         */
        Assets.button_exit = g.newPixmap("button_exit.png", PixmapFormat.ARGB4444);
        Assets.button_exit_pressed = g.newPixmap("button_exit_pressed.png", PixmapFormat.ARGB4444);
        Assets.button_start = g.newPixmap("button_start.png", PixmapFormat.ARGB4444);
        Assets.button_start_pressed = g.newPixmap("button_start_pressed.png", PixmapFormat.ARGB4444);
        Assets.button_help = g.newPixmap("button_help.png", PixmapFormat.ARGB4444);
        Assets.button_help_pressed = g.newPixmap("button_help_pressed.png", PixmapFormat.ARGB4444);
        Assets.button_exit1 = g.newPixmap("button_exit1.png", PixmapFormat.ARGB4444);
        Assets.button_exit_pressed1 = g.newPixmap("button_exit_pressed1.png", PixmapFormat.ARGB4444);
        /*
         * ����ѡ�񲿷�
         */
        Assets.sectionofMap = g.newPixmap("sectionofMap.png", PixmapFormat.ARGB4444);
        Assets.hero = g.newPixmap("hero.png", PixmapFormat.ARGB4444);
        Assets.heroPressed = g.newPixmap("heroPressed.png", PixmapFormat.ARGB4444);
        Assets.heroUnusable = g.newPixmap("heroUnusable.png", PixmapFormat.ARGB4444);
        Assets.warStartPressed = g.newPixmap("warStartPressed.png", PixmapFormat.ARGB4444);
        Assets.warStart = g.newPixmap("warStart.png", PixmapFormat.ARGB4444);
        Assets.choosePressed = g.newPixmap("choosePressed.png", PixmapFormat.ARGB4444);
        Assets.choose = g.newPixmap("choose.png", PixmapFormat.ARGB4444);
        
        Assets.dizhu = g.newPixmap("dizhu.png", PixmapFormat.ARGB4444);
        Assets.cardBack = g.newPixmap("cardback.png", PixmapFormat.ARGB4444);
        
        /*
         * ��ͷ��
         */
        Assets.LBCry = g.newPixmap("LBCry.png", PixmapFormat.ARGB4444);
        Assets.LBNomal = g.newPixmap("LBNomal.png", PixmapFormat.ARGB4444);
        Assets.LBSmill = g.newPixmap("LBSmill.png", PixmapFormat.ARGB4444);
        Assets.SQCry = g.newPixmap("SQCry.png", PixmapFormat.ARGB4444);
        Assets.SQNomal = g.newPixmap("SQNormal.png", PixmapFormat.ARGB4444);
        Assets.SQSmill = g.newPixmap("SQSmill.png", PixmapFormat.ARGB4444);
        Assets.CCCry = g.newPixmap("CCCry.png", PixmapFormat.ARGB4444);
        Assets.CCNomal = g.newPixmap("CCNormal.png", PixmapFormat.ARGB4444);
        Assets.CCSmill = g.newPixmap("CCSmill.png", PixmapFormat.ARGB4444);
        /*
         * ͷ��
         */
        Assets.playerD = g.newPixmap("playerD.png", PixmapFormat.ARGB4444);
        Assets.playerM = g.newPixmap("playerM.png", PixmapFormat.ARGB4444);
        Assets.playerU = g.newPixmap("playerU.png", PixmapFormat.ARGB4444);
        Assets.playerD_1 = g.newPixmap("playerD_1.png", PixmapFormat.ARGB4444);
        Assets.playerM_1 = g.newPixmap("playerM_1.png", PixmapFormat.ARGB4444);
        Assets.playerU_1 = g.newPixmap("playerU_1.png", PixmapFormat.ARGB4444);
        
        /*
         * ����
         */
        Assets.CC = g.newPixmap("caocao.png", PixmapFormat.ARGB4444);
        Assets.SQ = g.newPixmap("sunquan.png", PixmapFormat.ARGB4444);
        Assets.LB = g.newPixmap("liubei.png", PixmapFormat.ARGB4444);
        
        /*
         * ��Ϸ����
         */
        Assets.sectionOfScore = g.newPixmap("sectionofScore.png", PixmapFormat.ARGB4444);
        Assets.infoChar = g.newPixmap("infoChar.png", PixmapFormat.ARGB4444);
        Assets.infoHighLight = g.newPixmap("infoHighLight.png", PixmapFormat.ARGB4444);
        Assets.times = g.newPixmap("times.png", PixmapFormat.ARGB4444);
        Assets.info = g.newPixmap("icon_map.png", PixmapFormat.ARGB4444);
        Assets.infoPressed = g.newPixmap("icon_map_pressed.png", PixmapFormat.ARGB4444);
        Assets.close = g.newPixmap("close.png", PixmapFormat.ARGB4444);
        Assets.closePressed = g.newPixmap("closePressed.png", PixmapFormat.ARGB4444);
        Assets.left = g.newPixmap("turnLeft.png", PixmapFormat.ARGB4444);
        Assets.right = g.newPixmap("turnRight.png", PixmapFormat.ARGB4444);
        Assets.turnLiftPressed = g.newPixmap("turnLiftPressed.png", PixmapFormat.ARGB4444);
        Assets.turnRightPressed = g.newPixmap("turnRightPressed.png", PixmapFormat.ARGB4444);
        Assets.bingliIcon = g.newPixmap("bingliIcon.png", PixmapFormat.ARGB4444);
        Assets.citysIcon = g.newPixmap("citysIcon.png", PixmapFormat.ARGB4444);
        
        /*
         * ��ֻ���?
         */
        Assets.beishu = g.newPixmap("beishu.png", PixmapFormat.ARGB4444);
        Assets.bingli = g.newPixmap("bingli.png", PixmapFormat.ARGB4444);
        Assets.cityEarned = g.newPixmap("cityEarned.png", PixmapFormat.ARGB4444);
        Assets.citys = g.newPixmap("citys.png", PixmapFormat.ARGB4444);
        Assets.heroEarned = g.newPixmap("heroEarned.png", PixmapFormat.ARGB4444);
        Assets.nextCityScore = g.newPixmap("nextCityScore.png", PixmapFormat.ARGB4444);
        Assets.scoreOwned = g.newPixmap("scoreOwned.png", PixmapFormat.ARGB4444);
        Assets.zhadan = g.newPixmap("zhadan.png", PixmapFormat.ARGB4444);
        Assets.zhankuang = g.newPixmap("zhankuang.png", PixmapFormat.ARGB4444);
        Assets.okUnusable = g.newPixmap("okUnusable.png", PixmapFormat.ARGB4444);
        
        /*
         * ��ֲ���?
         */
        Assets.p10 = g.newPixmap("p10.png", PixmapFormat.ARGB4444);
        Assets.p20 = g.newPixmap("p20.png", PixmapFormat.ARGB4444);
        Assets.p50 = g.newPixmap("p50.png", PixmapFormat.ARGB4444);
        Assets.p100 = g.newPixmap("p100.png", PixmapFormat.ARGB4444);
        Assets.p200 = g.newPixmap("p200.png", PixmapFormat.ARGB4444);
        Assets.p500 = g.newPixmap("p500.png", PixmapFormat.ARGB4444);
        Assets.p10p = g.newPixmap("p10p.png", PixmapFormat.ARGB4444);
        Assets.p20p = g.newPixmap("p20p.png", PixmapFormat.ARGB4444);
        Assets.p50p = g.newPixmap("p50p.png", PixmapFormat.ARGB4444);
        Assets.p100p = g.newPixmap("p100p.png", PixmapFormat.ARGB4444);
        Assets.p200p = g.newPixmap("p200p.png", PixmapFormat.ARGB4444);
        Assets.p500p = g.newPixmap("p500p.png", PixmapFormat.ARGB4444);
        Assets.getpoints = g.newPixmap("getpoints.png", PixmapFormat.ARGB4444);
        Assets.getpointsp = g.newPixmap("getpointsp.png", PixmapFormat.ARGB4444);
        Assets.points = g.newPixmap("points.png", PixmapFormat.ARGB4444);
        Assets.pointsp = g.newPixmap("pointsp.png", PixmapFormat.ARGB4444);
        Assets.refreshpoints = g.newPixmap("refreshpoints.png", PixmapFormat.ARGB4444);
        Assets.refreshpointsp = g.newPixmap("refreshpointsp.png", PixmapFormat.ARGB4444);
        Assets.scoreThis = g.newPixmap("scorethis.png", PixmapFormat.ARGB4444);
               
        /*
         * �佫
         */
        Assets.heros[0] = g.newPixmap("h00.png", PixmapFormat.ARGB4444);
        Assets.heros[1] = g.newPixmap("h01.png", PixmapFormat.ARGB4444);
        Assets.heros[2] = g.newPixmap("h02.png", PixmapFormat.ARGB4444);
        Assets.heros[3] = g.newPixmap("h03.png", PixmapFormat.ARGB4444);
        Assets.heros[4] = g.newPixmap("h04.png", PixmapFormat.ARGB4444);
        Assets.heros[5] = g.newPixmap("h05.png", PixmapFormat.ARGB4444);
        Assets.heros[6] = g.newPixmap("h06.png", PixmapFormat.ARGB4444);
        Assets.heros[7] = g.newPixmap("h07.png", PixmapFormat.ARGB4444);
        Assets.heros[8] = g.newPixmap("h08.png", PixmapFormat.ARGB4444);
        Assets.heros[9] = g.newPixmap("h09.png", PixmapFormat.ARGB4444);
        Assets.heros[10] = g.newPixmap("hero10.png", PixmapFormat.ARGB4444);
        Assets.heros[11] = g.newPixmap("hero11.png", PixmapFormat.ARGB4444);
        Assets.heros[12] = g.newPixmap("hero12.png", PixmapFormat.ARGB4444);
        Assets.heros[13] = g.newPixmap("hero13.png", PixmapFormat.ARGB4444);
        Assets.heros[14] = g.newPixmap("h14.png", PixmapFormat.ARGB4444);
        Assets.heros[15] = g.newPixmap("h15.png", PixmapFormat.ARGB4444);
        Assets.heros[16] = g.newPixmap("h16.png", PixmapFormat.ARGB4444);
        Assets.heros[17] = g.newPixmap("h17.png", PixmapFormat.ARGB4444);
        Assets.heros[18] = g.newPixmap("h18.png", PixmapFormat.ARGB4444);
        Assets.heros[19] = g.newPixmap("h19.png", PixmapFormat.ARGB4444);
        Assets.heros[20] = g.newPixmap("h20.png", PixmapFormat.ARGB4444);
        Assets.heros[21] = g.newPixmap("h21.png", PixmapFormat.ARGB4444);
        Assets.heros[22] = g.newPixmap("h22.png", PixmapFormat.ARGB4444);
        Assets.heros[23] = g.newPixmap("h23.png", PixmapFormat.ARGB4444);
        Assets.heros[24] = g.newPixmap("h24.png", PixmapFormat.ARGB4444);
        Assets.heros[25] = g.newPixmap("h25.png", PixmapFormat.ARGB4444);
        Assets.heros[26] = g.newPixmap("h26.png", PixmapFormat.ARGB4444);
        Assets.heros[27] = g.newPixmap("h27.png", PixmapFormat.ARGB4444);
        Assets.heros[28] = g.newPixmap("h28.png", PixmapFormat.ARGB4444);
        Assets.heros[29] = g.newPixmap("h29.png", PixmapFormat.ARGB4444);
        Assets.heros[30] = g.newPixmap("h30.png", PixmapFormat.ARGB4444);
        Assets.heros[31] = g.newPixmap("h31.png", PixmapFormat.ARGB4444);
        Assets.heros[32] = g.newPixmap("h32.png", PixmapFormat.ARGB4444);
        Assets.heros[33] = g.newPixmap("h33.png", PixmapFormat.ARGB4444);
        Assets.heros[34] = g.newPixmap("h34.png", PixmapFormat.ARGB4444);
        Assets.heros[35] = g.newPixmap("h35.png", PixmapFormat.ARGB4444);
        Assets.heros[36] = g.newPixmap("h36.png", PixmapFormat.ARGB4444);
        Assets.heros[37] = g.newPixmap("h37.png", PixmapFormat.ARGB4444);
        Assets.heros[38] = g.newPixmap("h38.png", PixmapFormat.ARGB4444);
        Assets.heros[39] = g.newPixmap("h39.png", PixmapFormat.ARGB4444);
        Assets.heros[40] = g.newPixmap("h40.png", PixmapFormat.ARGB4444);
        Assets.heros[41] = g.newPixmap("h41.png", PixmapFormat.ARGB4444);
        Assets.heros[42] = g.newPixmap("h42.png", PixmapFormat.ARGB4444);
        Assets.heros[43] = g.newPixmap("h43.png", PixmapFormat.ARGB4444);
        Assets.heros[44] = g.newPixmap("h44.png", PixmapFormat.ARGB4444);
        Assets.heros[45] = g.newPixmap("h45.png", PixmapFormat.ARGB4444);
        Assets.heros[46] = g.newPixmap("h46.png", PixmapFormat.ARGB4444);
        Assets.heros[47] = g.newPixmap("h47.png", PixmapFormat.ARGB4444);
        Assets.heros[48] = g.newPixmap("h48.png", PixmapFormat.ARGB4444);
        Assets.heros[49] = g.newPixmap("h49.png", PixmapFormat.ARGB4444);
        Assets.heros[50] = g.newPixmap("h50.png", PixmapFormat.ARGB4444);
        Assets.heros[51] = g.newPixmap("h51.png", PixmapFormat.ARGB4444);
        Assets.heros[52] = g.newPixmap("h52.png", PixmapFormat.ARGB4444);
        Assets.heros[53] = g.newPixmap("h53.png", PixmapFormat.ARGB4444);
        Assets.heros[54] = g.newPixmap("h54.png", PixmapFormat.ARGB4444);
        Assets.heros[55] = g.newPixmap("h55.png", PixmapFormat.ARGB4444);
        Assets.heros[56] = g.newPixmap("h56.png", PixmapFormat.ARGB4444);
        Assets.heros[57] = g.newPixmap("h57.png", PixmapFormat.ARGB4444);
        Assets.heros[58] = g.newPixmap("h58.png", PixmapFormat.ARGB4444);
        Assets.heros[59] = g.newPixmap("h59.png", PixmapFormat.ARGB4444);
        Assets.heros[60] = g.newPixmap("h60.png", PixmapFormat.ARGB4444);
        Assets.heros[61] = g.newPixmap("h61.png", PixmapFormat.ARGB4444);
        Assets.heros[62] = g.newPixmap("h62.png", PixmapFormat.ARGB4444);
        Assets.heros[63] = g.newPixmap("h63.png", PixmapFormat.ARGB4444);
        Assets.heros[64] = g.newPixmap("h64.png", PixmapFormat.ARGB4444);
        Assets.heros[65] = g.newPixmap("h65.png", PixmapFormat.ARGB4444);
        Assets.heros[66] = g.newPixmap("h66.png", PixmapFormat.ARGB4444);
        Assets.heros[67] = g.newPixmap("h67.png", PixmapFormat.ARGB4444);
        Assets.heros[68] = g.newPixmap("h68.png", PixmapFormat.ARGB4444);
        Assets.heros[69] = g.newPixmap("h69.png", PixmapFormat.ARGB4444);
        Assets.heros[70] = g.newPixmap("h70.png", PixmapFormat.ARGB4444);
        Assets.heros[71] = g.newPixmap("h71.png", PixmapFormat.ARGB4444);
        Assets.heros[72] = g.newPixmap("h72.png", PixmapFormat.ARGB4444);
        Assets.heros[73] = g.newPixmap("h73.png", PixmapFormat.ARGB4444);
        Assets.heros[74] = g.newPixmap("h74.png", PixmapFormat.ARGB4444);
        Assets.heros[75] = g.newPixmap("h75.png", PixmapFormat.ARGB4444);
        Assets.heros[76] = g.newPixmap("h76.png", PixmapFormat.ARGB4444);
        Assets.heros[77] = g.newPixmap("h77.png", PixmapFormat.ARGB4444);
        Assets.heros[78] = g.newPixmap("h78.png", PixmapFormat.ARGB4444);
        Assets.heros[79] = g.newPixmap("h79.png", PixmapFormat.ARGB4444);
        Assets.heros[80] = g.newPixmap("h80.png", PixmapFormat.ARGB4444);
        Assets.heros[81] = g.newPixmap("h81.png", PixmapFormat.ARGB4444);
        Assets.heros[82] = g.newPixmap("h82.png", PixmapFormat.ARGB4444);
        Assets.heros[83] = g.newPixmap("h83.png", PixmapFormat.ARGB4444);
        Assets.heros[84] = g.newPixmap("h84.png", PixmapFormat.ARGB4444);
        Assets.heros[85] = g.newPixmap("h85.png", PixmapFormat.ARGB4444);
        Assets.heros[86] = g.newPixmap("h86.png", PixmapFormat.ARGB4444);
        Assets.heros[87] = g.newPixmap("h87.png", PixmapFormat.ARGB4444);
        Assets.heros[88] = g.newPixmap("h88.png", PixmapFormat.ARGB4444);
        Assets.heros[89] = g.newPixmap("h89.png", PixmapFormat.ARGB4444);
        
        Assets.heroIn = g.newPixmap("heroIn.png", PixmapFormat.ARGB4444);
        
        Assets.LiuBei = g.newPixmap("LiuBei.jpg", PixmapFormat.ARGB4444);
        
        Assets.sJoker = g.newPixmap("j1.png", PixmapFormat.ARGB4444);
        Assets.bJoker = g.newPixmap("j2.png", PixmapFormat.ARGB4444);
        
        Assets.wrongCards = g.newPixmap("wrongCards.png", PixmapFormat.ARGB4444);
        Assets.noBiggerCards = g.newPixmap("noBiggerCards.png", PixmapFormat.ARGB4444);
        
        Assets.diamond[0] = g.newPixmap("d1.png", PixmapFormat.ARGB4444);
        Assets.diamond[1] = g.newPixmap("d2.png", PixmapFormat.ARGB4444);
        Assets.diamond[2] = g.newPixmap("d3.png", PixmapFormat.ARGB4444);
        Assets.diamond[3] = g.newPixmap("d4.png", PixmapFormat.ARGB4444);
        Assets.diamond[4] = g.newPixmap("d5.png", PixmapFormat.ARGB4444);
        Assets.diamond[5] = g.newPixmap("d6.png", PixmapFormat.ARGB4444);
        Assets.diamond[6] = g.newPixmap("d7.png", PixmapFormat.ARGB4444);
        Assets.diamond[7] = g.newPixmap("d8.png", PixmapFormat.ARGB4444);
        Assets.diamond[8] = g.newPixmap("d9.png", PixmapFormat.ARGB4444);
        Assets.diamond[9] = g.newPixmap("d10.png", PixmapFormat.ARGB4444);
        Assets.diamond[10] = g.newPixmap("d11.png", PixmapFormat.ARGB4444);
        Assets.diamond[11] = g.newPixmap("d12.png", PixmapFormat.ARGB4444);
        Assets.diamond[12] = g.newPixmap("d13.png", PixmapFormat.ARGB4444);
        
        Assets.diamondS[0] = g.newPixmap("d1s.png", PixmapFormat.ARGB4444);
        Assets.diamondS[1] = g.newPixmap("d2s.png", PixmapFormat.ARGB4444);
        Assets.diamondS[2] = g.newPixmap("d3s.png", PixmapFormat.ARGB4444);
        Assets.diamondS[3] = g.newPixmap("d4s.png", PixmapFormat.ARGB4444);
        Assets.diamondS[4] = g.newPixmap("d5s.png", PixmapFormat.ARGB4444);
        Assets.diamondS[5] = g.newPixmap("d6s.png", PixmapFormat.ARGB4444);
        Assets.diamondS[6] = g.newPixmap("d7s.png", PixmapFormat.ARGB4444);
        Assets.diamondS[7] = g.newPixmap("d8s.png", PixmapFormat.ARGB4444);
        Assets.diamondS[8] = g.newPixmap("d9s.png", PixmapFormat.ARGB4444);
        Assets.diamondS[9] = g.newPixmap("d10s.png", PixmapFormat.ARGB4444);
        Assets.diamondS[10] = g.newPixmap("d11s.png", PixmapFormat.ARGB4444);
        Assets.diamondS[11] = g.newPixmap("d12s.png", PixmapFormat.ARGB4444);
        Assets.diamondS[12] = g.newPixmap("d13s.png", PixmapFormat.ARGB4444);
        
        Assets.club[0] = g.newPixmap("c1.png", PixmapFormat.ARGB4444);
        Assets.club[1] = g.newPixmap("c2.png", PixmapFormat.ARGB4444);
        Assets.club[2] = g.newPixmap("c3.png", PixmapFormat.ARGB4444);
        Assets.club[3] = g.newPixmap("c4.png", PixmapFormat.ARGB4444);
        Assets.club[4] = g.newPixmap("c5.png", PixmapFormat.ARGB4444);
        Assets.club[5] = g.newPixmap("c6.png", PixmapFormat.ARGB4444);
        Assets.club[6] = g.newPixmap("c7.png", PixmapFormat.ARGB4444);
        Assets.club[7] = g.newPixmap("c8.png", PixmapFormat.ARGB4444);
        Assets.club[8] = g.newPixmap("c9.png", PixmapFormat.ARGB4444);
        Assets.club[9] = g.newPixmap("c10.png", PixmapFormat.ARGB4444);
        Assets.club[10] = g.newPixmap("c11.png", PixmapFormat.ARGB4444);
        Assets.club[11] = g.newPixmap("c12.png", PixmapFormat.ARGB4444);
        Assets.club[12] = g.newPixmap("c13.png", PixmapFormat.ARGB4444);
        
        Assets.clubS[0] = g.newPixmap("c1s.png", PixmapFormat.ARGB4444);
        Assets.clubS[1] = g.newPixmap("c2s.png", PixmapFormat.ARGB4444);
        Assets.clubS[2] = g.newPixmap("c3s.png", PixmapFormat.ARGB4444);
        Assets.clubS[3] = g.newPixmap("c4s.png", PixmapFormat.ARGB4444);
        Assets.clubS[4] = g.newPixmap("c5s.png", PixmapFormat.ARGB4444);
        Assets.clubS[5] = g.newPixmap("c6s.png", PixmapFormat.ARGB4444);
        Assets.clubS[6] = g.newPixmap("c7s.png", PixmapFormat.ARGB4444);
        Assets.clubS[7] = g.newPixmap("c8s.png", PixmapFormat.ARGB4444);
        Assets.clubS[8] = g.newPixmap("c9s.png", PixmapFormat.ARGB4444);
        Assets.clubS[9] = g.newPixmap("c10s.png", PixmapFormat.ARGB4444);
        Assets.clubS[10] = g.newPixmap("c11s.png", PixmapFormat.ARGB4444);
        Assets.clubS[11] = g.newPixmap("c12s.png", PixmapFormat.ARGB4444);
        Assets.clubS[12] = g.newPixmap("c13s.png", PixmapFormat.ARGB4444);
        
        Assets.heart[0] = g.newPixmap("h1.png", PixmapFormat.ARGB4444);
        Assets.heart[1] = g.newPixmap("h2.png", PixmapFormat.ARGB4444);
        Assets.heart[2] = g.newPixmap("h3.png", PixmapFormat.ARGB4444);
        Assets.heart[3] = g.newPixmap("h4.png", PixmapFormat.ARGB4444);
        Assets.heart[4] = g.newPixmap("h5.png", PixmapFormat.ARGB4444);
        Assets.heart[5] = g.newPixmap("h6.png", PixmapFormat.ARGB4444);
        Assets.heart[6] = g.newPixmap("h7.png", PixmapFormat.ARGB4444);
        Assets.heart[7] = g.newPixmap("h8.png", PixmapFormat.ARGB4444);
        Assets.heart[8] = g.newPixmap("h9.png", PixmapFormat.ARGB4444);
        Assets.heart[9] = g.newPixmap("h10.png", PixmapFormat.ARGB4444);
        Assets.heart[10] = g.newPixmap("h11.png", PixmapFormat.ARGB4444);
        Assets.heart[11] = g.newPixmap("h12.png", PixmapFormat.ARGB4444);
        Assets.heart[12] = g.newPixmap("h13.png", PixmapFormat.ARGB4444);
        
        Assets.heartS[0] = g.newPixmap("h1s.png", PixmapFormat.ARGB4444);
        Assets.heartS[1] = g.newPixmap("h2s.png", PixmapFormat.ARGB4444);
        Assets.heartS[2] = g.newPixmap("h3s.png", PixmapFormat.ARGB4444);
        Assets.heartS[3] = g.newPixmap("h4s.png", PixmapFormat.ARGB4444);
        Assets.heartS[4] = g.newPixmap("h5s.png", PixmapFormat.ARGB4444);
        Assets.heartS[5] = g.newPixmap("h6s.png", PixmapFormat.ARGB4444);
        Assets.heartS[6] = g.newPixmap("h7s.png", PixmapFormat.ARGB4444);
        Assets.heartS[7] = g.newPixmap("h8s.png", PixmapFormat.ARGB4444);
        Assets.heartS[8] = g.newPixmap("h9s.png", PixmapFormat.ARGB4444);
        Assets.heartS[9] = g.newPixmap("h10s.png", PixmapFormat.ARGB4444);
        Assets.heartS[10] = g.newPixmap("h11s.png", PixmapFormat.ARGB4444);
        Assets.heartS[11] = g.newPixmap("h12s.png", PixmapFormat.ARGB4444);
        Assets.heartS[12] = g.newPixmap("h13s.png", PixmapFormat.ARGB4444);
        
        Assets.spade[0] = g.newPixmap("s1.png", PixmapFormat.ARGB4444);
        Assets.spade[1] = g.newPixmap("s2.png", PixmapFormat.ARGB4444);
        Assets.spade[2] = g.newPixmap("s3.png", PixmapFormat.ARGB4444);
        Assets.spade[3] = g.newPixmap("s4.png", PixmapFormat.ARGB4444);
        Assets.spade[4] = g.newPixmap("s5.png", PixmapFormat.ARGB4444);
        Assets.spade[5] = g.newPixmap("s6.png", PixmapFormat.ARGB4444);
        Assets.spade[6] = g.newPixmap("s7.png", PixmapFormat.ARGB4444);
        Assets.spade[7] = g.newPixmap("s8.png", PixmapFormat.ARGB4444);
        Assets.spade[8] = g.newPixmap("s9.png", PixmapFormat.ARGB4444);
        Assets.spade[9] = g.newPixmap("s10.png", PixmapFormat.ARGB4444);
        Assets.spade[10] = g.newPixmap("s11.png", PixmapFormat.ARGB4444);
        Assets.spade[11] = g.newPixmap("s12.png", PixmapFormat.ARGB4444);
        Assets.spade[12] = g.newPixmap("s13.png", PixmapFormat.ARGB4444);
        
        Assets.spadeS[0] = g.newPixmap("s1s.png", PixmapFormat.ARGB4444);
        Assets.spadeS[1] = g.newPixmap("s2s.png", PixmapFormat.ARGB4444);
        Assets.spadeS[2] = g.newPixmap("s3s.png", PixmapFormat.ARGB4444);
        Assets.spadeS[3] = g.newPixmap("s4s.png", PixmapFormat.ARGB4444);
        Assets.spadeS[4] = g.newPixmap("s5s.png", PixmapFormat.ARGB4444);
        Assets.spadeS[5] = g.newPixmap("s6s.png", PixmapFormat.ARGB4444);
        Assets.spadeS[6] = g.newPixmap("s7s.png", PixmapFormat.ARGB4444);
        Assets.spadeS[7] = g.newPixmap("s8s.png", PixmapFormat.ARGB4444);
        Assets.spadeS[8] = g.newPixmap("s9s.png", PixmapFormat.ARGB4444);
        Assets.spadeS[9] = g.newPixmap("s10s.png", PixmapFormat.ARGB4444);
        Assets.spadeS[10] = g.newPixmap("s11s.png", PixmapFormat.ARGB4444);
        Assets.spadeS[11] = g.newPixmap("s12s.png", PixmapFormat.ARGB4444);
        Assets.spadeS[12] = g.newPixmap("s13s.png", PixmapFormat.ARGB4444);
        
        Assets.numbers = g.newPixmap("numbers.png", PixmapFormat.ARGB4444);
        
        Assets.backgroundInfo = g.newPixmap("background_info.png", PixmapFormat.ARGB4444);
        Assets.backgroundScore = g.newPixmap("background_score.png", PixmapFormat.ARGB4444);
        Assets.scoreInfo = g.newPixmap("score_info.png", PixmapFormat.ARGB4444);
        Assets.buttonNext = g.newPixmap("button_next.png", PixmapFormat.ARGB4444);
        Assets.buttonNextPressed = g.newPixmap("button_next_pressed.png", PixmapFormat.ARGB4444);
        Assets.buttonExit = g.newPixmap("button_exit.png", PixmapFormat.ARGB4444);
        Assets.buttonExitPressed = g.newPixmap("button_exit_pressed.png", PixmapFormat.ARGB4444);
        Assets.plus = g.newPixmap("plus.png", PixmapFormat.ARGB4444);
        Assets.minus = g.newPixmap("minus.png", PixmapFormat.ARGB4444);
        Assets.win = g.newPixmap("win.png", PixmapFormat.ARGB4444);
        Assets.lose = g.newPixmap("lose.png", PixmapFormat.ARGB4444);
        Assets.numbersScore = g.newPixmap("numbers_score.png", PixmapFormat.ARGB4444);
        Assets.numbersInfo = g.newPixmap("numbers_info.png", PixmapFormat.ARGB4444);
        Assets.numbersLevel = g.newPixmap("numbers_level.png", PixmapFormat.ARGB4444);
        Assets.levelProcess = g.newPixmap("level_process.png", PixmapFormat.ARGB4444);
        Assets.levelProcessBack = g.newPixmap("level_process_back.png", PixmapFormat.ARGB4444);
        Assets.gameoverDizhu = g.newPixmap("dz.png", PixmapFormat.ARGB4444);
        
        Assets.moreGames = g.newPixmap("button_moregames.png", PixmapFormat.ARGB4444);
        Assets.moreGamesPressed = g.newPixmap("button_moregames_pressed.png", PixmapFormat.ARGB4444);
        
        Assets.musicOn = g.newPixmap("music_on.png", PixmapFormat.ARGB4444); 
        Assets.musicOff = g.newPixmap("music_off.png", PixmapFormat.ARGB4444); 
        
        Assets.soundOn = g.newPixmap("sound_on.png", PixmapFormat.ARGB4444); 
        Assets.soundOff = g.newPixmap("sound_off.png", PixmapFormat.ARGB4444); 
     
        Assets.keeper = g.newPixmap("keeper.png", PixmapFormat.ARGB4444); 
        Assets.keeperPressed = g.newPixmap("keeper_pressed.png", PixmapFormat.ARGB4444); 
        
        Assets.getPoints = g.newPixmap("get_points.png", PixmapFormat.ARGB4444); 
        
        Assets.fold = game.getAudio().newSound("fold.mp3");
        Assets.one = game.getAudio().newSound("one.mp3");
        Assets.two = game.getAudio().newSound("two.mp3");
        Assets.three = game.getAudio().newSound("three.mp3");
        Assets.shun = game.getAudio().newSound("shunzi.wav");
        Assets.shun2 = game.getAudio().newSound("liandui.wav");
        Assets.feiji = game.getAudio().newSound("feiji.wav");
        Assets.ssJoker = game.getAudio().newSound("xiaowang.wav");
        Assets.sbJoker = game.getAudio().newSound("dawang.wav");
        Assets.huo = game.getAudio().newSound("wangzha.wav");
        Assets.sanWithDan = game.getAudio().newSound("sandaiyi.wav");
        Assets.sanWithDui = game.getAudio().newSound("sandaiyidui.wav");
        Assets.sizWithTwoDan = game.getAudio().newSound("sidaier.wav");
        Assets.sizWithTwoDui = game.getAudio().newSound("sidailiangdui.wav");
        Assets.zha = game.getAudio().newSound("zhadan.wav");
        Assets.pass1 = game.getAudio().newSound("pass.wav");
        Assets.pass2 = game.getAudio().newSound("buyao.wav");
        Assets.pass3 = game.getAudio().newSound("yaobuqi.wav");
        Assets.da1 = game.getAudio().newSound("dani1.wav");
        Assets.da2 = game.getAudio().newSound("dani2.wav");
        Assets.da3 = game.getAudio().newSound("dani3.wav");
        Assets.boom = game.getAudio().newSound("boom.wav");
        Assets.keeperMove = game.getAudio().newSound("menu.wav");
        Assets.rechoose = game.getAudio().newSound("rechoose.mp3");
        Assets.chooseLevel = game.getAudio().newSound("button.mp3");
        Assets.sMusicOn = game.getAudio().newSound("music_button.ogg");
        Assets.outCard = game.getAudio().newSound("givecard.wav");
        
        Assets.sWin = game.getAudio().newSound("win.mp3");
        Assets.sLose = game.getAudio().newSound("lose.mp3");
        
        Settings.load(game.getFileIO());
        game.setScreen(new MainMenuScreen(game));
    }

    @Override
    public void present(float deltaTime) {
    	
    	
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}