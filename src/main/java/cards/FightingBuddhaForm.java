package cards;

import Helpers.ModHelper;
import actions.DodgeTrainingAction;
import actions.IncreaseCostAction;
import basemod.BaseMod;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.RegenPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import demoMod.MonkeyKingMod;
import pathes.AbstractCardEnum;

public class FightingBuddhaForm extends CustomCard {
    public static final String ID = "FightingBuddhaForm";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ModHelper.MakePath(ID));
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 9;
    private static final int ATTACK_AMT = 5;
    private static final int UPGRADE_PLUS_ATTACK = 2;
    private static final int BLOCK_AMT = 9;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    private static final int MAGIC_AMT = 1;
    private static final int UPGRADE_PLUS_MAGIC_AMT = 1;
    private static int ELITE_KILLED = 0;
    private  boolean dec;
    public static final String IMG_PATH = "img/cards/fighting_buddha_form.png";

    public FightingBuddhaForm() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.POWER, AbstractCardEnum.MonkeyKing_RED, CardRarity.RARE, CardTarget.SELF);
        this.baseBlock=BLOCK_AMT;
        this.block=this.baseBlock;
        this.baseMagicNumber = MAGIC_AMT;
        this.magicNumber = this.baseMagicNumber;
        this.selfRetain = true;
        this.dec =false;
    }



    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p,p,new StrengthPower(p,9),9));
        this.addToBot(new ApplyPowerAction(p,p,new DexterityPower(p,9),9));
        this.addToBot(new HealAction(p,p,8));
        this.addToBot(new ApplyPowerAction(p,p,new ArtifactPower(p,1),1));
    }

    public void onRetained() {
        this.updateCost(-this.magicNumber);
    }

    public void triggerWhenDrawn(){
        //BaseMod.logger.warn((CardCrawlGame.elites1Slain+CardCrawlGame.elites2Slain+CardCrawlGame.elites3Slain));
        if (!dec){
            int cost1 = (CardCrawlGame.elites1Slain+CardCrawlGame.elites2Slain+CardCrawlGame.elites3Slain);
            this.addToBot(new IncreaseCostAction(this.uuid, -cost1*1));
            BaseMod.logger.info("FightingBuddhaForm cost:"+cost1);
            dec=true;
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new FightingBuddhaForm();
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.isInnate = true;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}