package events.act1;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.RoomEventDialog;
import com.megacrit.cardcrawl.events.exordium.GoopPuddle;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.beyond.Exploder;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import monsters.act1.Loong;
import relics.RuyiJingguBang;

public class LoongPalace extends AbstractImageEvent {
    public static final String ID = "dreaming_journey_to_the_west:Loong Palace";
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String NAME = eventStrings.NAME;
    private LoongPalace.CurScreen screen;
    private Texture bgImg = ImageMaster.loadImage("img/scene/zmxy1-3.png");
    private int goldLoss;

    public LoongPalace() {
        super(NAME, DESCRIPTIONS[0], "img/events/loong_palace.png");
        this.screen = LoongPalace.CurScreen.INTRO;
        this.imageEventText.clearAllDialogs();
        if (AbstractDungeon.ascensionLevel >= 15) {
            this.goldLoss = AbstractDungeon.miscRng.random(55, 75);
        } else {
            this.goldLoss = AbstractDungeon.miscRng.random(40, 50);
        }
        this.imageEventText.setDialogOption(OPTIONS[0]+ this.goldLoss + OPTIONS[1]);
        this.imageEventText.setDialogOption(OPTIONS[2],new RuyiJingguBang());
    }




    @Override
    protected void buttonEffect(int buttonPressed) {
        switch(this.screen) {
            case INTRO:
                switch (buttonPressed) {
                    case 0:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        AbstractDungeon.player.loseGold(this.goldLoss);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[4]);
                        this.screen = LoongPalace.CurScreen.RESULT;
                        logMetricLoseGold("dreaming_journey_to_the_west:Loong Palace", "Left Gold", this.goldLoss);
                        this.openMap();
                        return;
                    case 1:
                        beginFight();
                        return;

            }
            case RESULT:
                this.openMap();
        }
    }

    private void beginFight() {
        this.screen = CurScreen.FIGHT;
        logMetric("dreaming_journey_to_the_west:Loong Palace", "Fought Loong");
        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
        (AbstractDungeon.getCurrRoom()).rewards.clear();
        if (!AbstractDungeon.player.hasRelic(RuyiJingguBang.ID)) {
            AbstractDungeon.getCurrRoom().addRelicToRewards(new RuyiJingguBang());
        }else {
            AbstractDungeon.getCurrRoom().addRelicToRewards(AbstractDungeon.returnRandomScreenlessRelic(AbstractRelic.RelicTier.RARE));
        }

        AbstractDungeon.getCurrRoom().addGoldToRewards(AbstractDungeon.miscRng.random(20, 30));
        (AbstractDungeon.getCurrRoom()).monsters = new MonsterGroup(new Loong(0.0F, 0.0F));
        this.imageEventText.clearAllDialogs();
        enterCombatFromImage();
    }

    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.draw(this.bgImg, 0.0F, 0.0F, (float) Settings.WIDTH, 1080.0F * Settings.scale);
    }

    public void dispose() {
        super.dispose();
        System.out.println("DISPOSING MUSHROOM ASSETS>");
        if (this.bgImg != null) {
            this.bgImg.dispose();
            this.bgImg = null;
        }

    }


    private static enum CurScreen {
        INTRO,
        FIGHT,
        RESULT;

        private CurScreen() {
        }
    }
}
