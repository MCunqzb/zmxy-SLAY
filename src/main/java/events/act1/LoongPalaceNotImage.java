package events.act1;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.RoomEventDialog;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import monsters.act1.Loong;
import relics.RuyiJingguBang;

public class LoongPalaceNotImage extends AbstractEvent {
    public static final String ID = "dreaming_journey_to_the_west:Loong Palace";
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String NAME = eventStrings.NAME;
    private Texture bgImg = ImageMaster.loadImage("img/scene/zmxy1-3.png");
    private Texture loong = ImageMaster.loadImage("img/monsters/loong.png");

    private int goldLoss;
    private int screenNum = 0;

    public LoongPalaceNotImage() {
        if (AbstractDungeon.ascensionLevel >= 15) {
            this.goldLoss = AbstractDungeon.miscRng.random(55, 75);
        } else {
            this.goldLoss = AbstractDungeon.miscRng.random(40, 50);
        }
        this.body= DESCRIPTIONS[0];
        this.roomEventText.addDialogOption(OPTIONS[2],new RuyiJingguBang());
        this.roomEventText.addDialogOption(OPTIONS[0]+ this.goldLoss + OPTIONS[1]);
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.EVENT;
        this.hasDialog = true;
        this.hasFocus = true;

    }

    public void update() {
        super.update();
        if (!RoomEventDialog.waitForInput) {
            this.buttonEffect(this.roomEventText.getSelectedOption());
        }

    }


    @Override
    protected void buttonEffect(int buttonPressed) {
        switch (buttonPressed) {
            case 0:
                if (screenNum==0){
                    AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter(Loong.ID);
                    this.roomEventText.updateBodyText(DESCRIPTIONS[1]);
                    this.roomEventText.updateDialogOption(0, OPTIONS[3]);
                    this.roomEventText.clearRemainingOptions();
                    logMetric("dreaming_journey_to_the_west:Loong Palace", "Fought Loong");
                    this.screenNum+=2;
                }else if (screenNum==1){
                    this.openMap();
                }else if (screenNum==2) {
                    AbstractDungeon.getCurrRoom().addGoldToRewards(AbstractDungeon.miscRng.random(20, 30));
                    if (!AbstractDungeon.player.hasRelic(RuyiJingguBang.ID)) {
                        AbstractDungeon.getCurrRoom().addRelicToRewards(new RuyiJingguBang());
                    }else {
                        AbstractDungeon.getCurrRoom().addRelicToRewards(AbstractDungeon.returnRandomScreenlessRelic(AbstractRelic.RelicTier.RARE));
                    }
                    this.enterCombat();
                    AbstractDungeon.lastCombatMetricKey = Loong.ID;
                }
                return;
            case 1:
                this.roomEventText.updateBodyText(DESCRIPTIONS[2]);
                AbstractDungeon.player.loseGold(this.goldLoss);
                this.roomEventText.updateDialogOption(0,OPTIONS[4]);
                this.roomEventText.clearRemainingOptions();
                logMetricLoseGold("dreaming_journey_to_the_west:Loong Palace", "Left Gold", this.goldLoss);
                this.screenNum = 1;
                return;
            }

    }

    private void beginFight() {

        logMetric("dreaming_journey_to_the_west:Loong Palace", "Fought Loong");
        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
        (AbstractDungeon.getCurrRoom()).rewards.clear();


        AbstractDungeon.getCurrRoom().addGoldToRewards(AbstractDungeon.miscRng.random(20, 30));
        (AbstractDungeon.getCurrRoom()).monsters = new MonsterGroup(new Loong(0.0F, 0.0F));
        this.imageEventText.clearAllDialogs();

    }

    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.draw(this.bgImg, 0.0F, 0.0F, (float) Settings.WIDTH, 1080.0F * Settings.scale);
        //sb.draw();
    }

    public void dispose() {
        super.dispose();
        System.out.println("DISPOSING MUSHROOM ASSETS>");
        if (this.bgImg != null) {
            this.bgImg.dispose();
            this.bgImg = null;
        }

    }



}
