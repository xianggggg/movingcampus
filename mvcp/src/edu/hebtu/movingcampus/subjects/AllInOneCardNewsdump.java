package edu.hebtu.movingcampus.subjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import edu.hebtu.movingcampus.biz.CardDao;
import edu.hebtu.movingcampus.config.Constants;
import edu.hebtu.movingcampus.entity.CardEntity;
import edu.hebtu.movingcampus.entity.NewsShort;
import edu.hebtu.movingcampus.enums.LocalNewsType;
import edu.hebtu.movingcampus.enums.NewsType;
import edu.hebtu.movingcampus.subject.base.Newsdump;
import edu.hebtu.movingcampus.subject.base.Subject;

/**
 * @author hippo
 * @version 1.0
 * @created 14-Nov-2013 9:13:30 AM
 */
public class AllInOneCardNewsdump extends Subject implements Newsdump,
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CardEntity m_Balance;
	public static int loweast;

	/**
	 * 
	 * @param bean
	 */
	public AllInOneCardNewsdump(Activity ac) {
		super(ac);
		SharedPreferences pre = ac.getSharedPreferences(
				Constants.PREFER_FILE, 0);
		AllInOneCardNewsdump.loweast=pre.getInt(Constants.BALANCE_LOWEAST, 10);
	}

	@Override
	public Boolean mesureChange() {
		m_Balance = new CardDao(ac).mapperJson(false);
		if (m_Balance != null && m_Balance.getCount() <= loweast)
			return true;
		return false;
	}

	@Override
	public List<NewsShort> dump() {
		List<NewsShort> list = new ArrayList<NewsShort>();
		if (mesureChange()) {
			// TODO
			NewsShort news = new NewsShort();
			news.setType(NewsType.O_LOCAL);
			news.setTitle("一卡通余额提醒:");
			news.setContent("您的一卡通余额不足"+loweast+"元,请尽快充值"+m_Balance.getCount() + " 元");
			news.setID(LocalNewsType.I_CARD_NOTIFY.ordinal() + 10);
			news.setDate(new Date());
			list.add(news);
		} 
		return list;
	}
}
