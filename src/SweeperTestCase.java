import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.junit.Test;

public class SweeperTestCase {

	@Test
	public void test() {
		Sweeper sweeper = new Sweeper(null);
		Calendar tempCalendar = Calendar.getInstance(Locale.JAPAN);
		List<Sweeper.SweeperPlaceCombination> prevSweeperPlaceCombinationList = null;
		int prevYear = 2016;
		tempCalendar.set(prevYear, Calendar.JANUARY, 1);
		
		for(int i=0, size=3660; i<size; i++){
			int currentYear = tempCalendar.get(Calendar.YEAR);
			int dayOfWeek = tempCalendar.get(Calendar.DAY_OF_WEEK);
			boolean supportDate = true;
			
			// 土日判定
			switch(dayOfWeek){
			case Calendar.SUNDAY:
			case Calendar.SATURDAY:
				// 土日はサポート対象外
				supportDate = false;
				break;
			default:
				break;
			}

			if(supportDate){
				if(prevYear != currentYear){
					// 年をまたいだ時は一旦インデックスが初期化されるので、比較の対象からは外す
					prevSweeperPlaceCombinationList = null;
				}
				
				List<Sweeper.SweeperPlaceCombination> currentSweeperPlaceCombinationList = sweeper.getSweeperPlaceCombinations(tempCalendar);
				if(prevSweeperPlaceCombinationList != null){
					for(int j=0, sizeJ=prevSweeperPlaceCombinationList.size(); j<sizeJ; j++){
						Sweeper.SweeperPlaceCombination currentSweeperPlaceCombination = currentSweeperPlaceCombinationList.get(j);
						Sweeper.SweeperPlaceCombination pervSweeperPlaceCombination = prevSweeperPlaceCombinationList.get((j + 1) % sizeJ);
						
						assert currentSweeperPlaceCombination.place.equals(pervSweeperPlaceCombination.place);
					}
				}
				
				prevSweeperPlaceCombinationList = currentSweeperPlaceCombinationList;
			}

			// 日付を次の日にずらす
			tempCalendar.set(Calendar.DAY_OF_YEAR, tempCalendar.get(Calendar.DAY_OF_YEAR) + 1);
		}
	}

}
