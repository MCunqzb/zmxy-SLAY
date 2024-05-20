package cards;

import Helpers.ModHelper;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import pathes.AbstractCardEnum;

import java.util.Iterator;


public class Overawe extends CustomCard {
    public static final String ID = "Overawe";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ModHelper.MakePath(ID));
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("dreaming_journey_to_the_west:MonkeyKingPhantom");
    public static final String[] DIALOG = monsterStrings.DIALOG;
    public static Settings.GameLanguage language;
    private int Translate = 0 ;
    private static final int COST =1;
    private static final int ATTACK_AMT = 14;
    private static final int UPGRADE_PLUS_ATTACK = 6;
    private static final int BLOCK_AMT = 0;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    private static final int MAGIC_AMT = 2;
    private static final int UPGRADE_PLUS_MAGIC_AMT = 1;
    public static final String IMG_PATH = "img/cards/overawe.png";


    public Overawe() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, AbstractCardEnum.MonkeyKing_RED, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        this.baseMagicNumber=MAGIC_AMT;
        this.magicNumber=this.baseMagicNumber;
        this.exhaust = true;
        //if(language == Settings.GameLanguage.ZHS){
        //    Translate = 0;
        //}
        //else
        //   Translate = 1;
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new SFXAction("INTIMIDATE"));
        this.addToBot(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, Settings.RED_TEXT_COLOR, ShockWaveEffect.ShockWaveType.CHAOTIC), 0.75F));
        this.addToBot((new TalkAction(true,DIALOG[0], 2.5F, 2.5F)));
        Iterator var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

        while(var3.hasNext()) {
            AbstractMonster mo = (AbstractMonster)var3.next();
            this.addToBot(new ApplyPowerAction(mo, p, new WeakPower(mo, this.magicNumber, false), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
        }
    }
    @Override
    public AbstractCard makeCopy() {
        return new Overawe();
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_MAGIC_AMT);
        }
    }
}
