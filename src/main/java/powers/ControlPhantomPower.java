package powers;

import Helpers.ModHelper;
import actions.ControlPhantomAction;
import cards.PhantomStab;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ControlPhantomPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.MakePath("ControlPhantomPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ControlPhantomPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        String path128 = "img/powers/control_phantom84.png";
        String path48 = "img/powers/control_phantom32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.updateDescription();
        this.isTurnBased = true;
    }

    public void atStartOfTurn() {
        this.flash();
        //for(int i = 0; i < (int)(TempHPField.tempHp.get(this.owner)/3)+1; ++i) {
        //    this.addToBot(new AbstractGameAction() {
        //        public void update() {
        //            this.addToBot(new PlayTopCardAction(AbstractDungeon.getCurrRoom().monsters.getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng), false));
        //            this.isDone = true;
        //        }
        //    });
        //}
        this.addToBot(new ControlPhantomAction((int)(TempHPField.tempHp.get(this.owner)/3)+1));

    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],(TempHPField.tempHp.get(this.owner)/3)+1);
    }
}
