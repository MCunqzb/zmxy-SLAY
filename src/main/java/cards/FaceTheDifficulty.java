package cards;

import Helpers.ModHelper;
import actions.FaceTheDifficultyAction;
import basemod.BaseMod;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.vfx.combat.RedFireballEffect;
import demoMod.MonkeyKingMod;
import pathes.AbstractCardEnum;
import powers.ScorchPower;

import java.util.Iterator;

public class FaceTheDifficulty extends CustomCard {
    public static final String ID = "FaceTheDifficulty";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ModHelper.MakePath(ID));
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST =1;
    private static final int ATTACK_AMT = 12;
    private static final int UPGRADE_PLUS_ATTACK = 4;
    private static final int BLOCK_AMT = 4;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    private static final int MAGIC_AMT = 0;
    private static final int UPGRADE_PLUS_MAGIC_AMT = 1;
    public static final String IMG_PATH = "img/cards/face_the_difficulty.png";
    private int totalBuffAmount = 0;

    public FaceTheDifficulty() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, AbstractCardEnum.MonkeyKing_RED, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        this.exhaust=true;
        this.baseMagicNumber=MAGIC_AMT;
        this.magicNumber=this.baseMagicNumber;

    }
    public void use(AbstractPlayer p, AbstractMonster m) {
            this.addToBot(new FaceTheDifficultyAction(p,this.upgraded));
        }

    @Override
    public AbstractCard makeCopy() {
        return new FaceTheDifficulty();
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
