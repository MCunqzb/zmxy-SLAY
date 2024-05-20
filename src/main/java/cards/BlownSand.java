package cards;

import Helpers.ModHelper;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.HeartBuffEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import pathes.AbstractCardEnum;


public class BlownSand extends CustomCard {
    public static final String ID = "BlownSand";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ModHelper.MakePath(ID));
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 0;
    private static final int ATTACK_AMT = 12;
    private static final int UPGRADE_PLUS_ATTACK = 4;
    private static final int BLOCK_AMT = 0;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    private static final int MAGIC_AMT = 2;
    private static final int UPGRADE_PLUS_MAGIC_AMT = 1;
    public static final String IMG_PATH = "img/cards/blown_sand.png";

    public BlownSand() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.STATUS, CardColor.COLORLESS, CardRarity.COMMON, CardTarget.SELF);
        this.baseMagicNumber=MAGIC_AMT;
        this.magicNumber=this.baseMagicNumber;
        this.exhaust = true;
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new LoseHPAction(p,p,this.magicNumber));
    }
    @Override
    public AbstractCard makeCopy() {
        return new BlownSand();
    }


    @Override
    public void upgrade() {

    }
}
