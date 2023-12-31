package cards;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawReductionPower;
import com.megacrit.cardcrawl.powers.EntanglePower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;
import pathes.AbstractCardEnum;

import java.util.Iterator;

public class ShiningPillar extends CustomCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("dreaming_journey_to_the_west:ShiningPillar");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/shining_pillar.png";
    private static final int COST = 3;
    private static final int ATTACK_DMG = 28;
    private static final int MAGIC_NUM = 1;
    private static final int UPGRADE_PLUS_DMG = 7;
    public static final String ID = "ShiningPillar";

    public ShiningPillar() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, AbstractCardEnum.MonkeyKing_RED, CardRarity.RARE, CardTarget.ALL_ENEMY);
        this.baseDamage = ATTACK_DMG;
        this.baseMagicNumber = MAGIC_NUM;
        this.magicNumber=this.baseMagicNumber;
        this.isMultiDamage = true;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Iterator var1 = AbstractDungeon.getMonsters().monsters.iterator();
        while(var1.hasNext()) {
            AbstractMonster m1 = (AbstractMonster)var1.next();
            if (!m1.isDead && !m1.isDying) {
                this.addToBot(new VFXAction(new WeightyImpactEffect(m1.hb.cX, m1.hb.cY, new Color(1.0F, 0.1F, 0.1F, 0.0F))));
            }
        }
        //for (AbstractMonster mon : (AbstractDungeon.getCurrRoom()).monsters.monsters)
            
        this.addToBot(new WaitAction(0.8F));
        this.addToBot( new DamageAllEnemiesAction(p,this.multiDamage,this.damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        this.addToBot(new ApplyPowerAction(p,p,new DrawReductionPower(p,this.magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() {
        return (AbstractCard)new ShiningPillar();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);

        }
    }
}
