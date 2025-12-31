package db;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AppearServlet")
public class AppearServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AppearServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String item = request.getParameter("item"); // 並び替えの項目
		String order = request.getParameter("order"); // asc:昇順 desc:降順
		String submit = request.getParameter("submit"); // "並び替え" "登録" "削除"
		String newnumber = request.getParameter("newnumber"); // 登録するポケモン番号
		String newshicode = request.getParameter("newshicode"); // 登録する市コード
		String deleteid = request.getParameter("deleteid"); // 削除するID
		String shimei = request.getParameter("shimei"); // 市名をクリックした場合
		
		String select = request.getParameter("select"); //タイプのセレクトボックス
		String select2 = request.getParameter("select2"); //2個目のタイプのセレクトボックス
		String getNum = request.getParameter("getNum");//捕まえる数
		
		System.out.printf("\n%s:%s:%s:\n", item, order, submit);
		System.out.printf("%s:%s:\n", newnumber, newshicode);
		System.out.printf("%s:%s:\n", deleteid, shimei);
		if (submit != null) {
			if (submit.equals("並び替え")) { // この場合は特に何もしない
			} else if (submit.equals("登録")) {
				insert(newnumber, newshicode);
			} else if (submit.equals("削除")) {
				delete(deleteid);
			} else if (submit.equals("出現させる"))
				insert(newnumber, newshicode, getNum);
		}
		selectAll(request, response, item, order, select, select2);
		RequestDispatcher dispatcher = request.getRequestDispatcher("appear.jsp");
		dispatcher.forward(request, response);
		
	}

	/** サーブレットがPOSTメソッドで実行された場合でもdoGet()で処理する */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/** DAOを呼び出す */
	void selectAll(HttpServletRequest request, HttpServletResponse response, String item, String order, String select, String select2)
			throws ServletException {
		AppearDAO appearDAO = new AppearDAO();
		
		//初めにfindAllを行う
		List<Appear> list = appearDAO.findAll();

		//itemとorderがnullでなければitem,orderを引数にしてfindAllを呼び出す
		if(item != null && order != null) {
			list = appearDAO.findAll(item, order);
		}
		
		// 重複した情報をまとめる(複数タイプを持ったポケモンは2体表示されるため)
		list = checkDup(list);
		
		
		if(select != null && select2 != null) {
			if(!select.equals("指定なし") ||  !select2.equals("指定なし")) {
				list = selectType(list, select, select2);
			}
		}
		
		request.setAttribute("list", list);
	}

	/** DAOを呼び出す */
	void insert(String newnumber, String newshicode) {
		try {
			int num = Integer.parseInt(newnumber);
			int code = Integer.parseInt(newshicode);
			AppearDAO appearDAO = new AppearDAO();
			
			
			List<Integer> shiList = appearDAO.shiList();
			if(!shiList.contains(code)) throw new NumberFormatException("市コードに含まれない数です");
			
			boolean success = appearDAO.insert(num, code);
			System.out.println(success);
		} catch (NumberFormatException e) {
			System.out.println("不正な番号または市コードが入力されました\n" + e.getMessage());
		}
	}
	
	void insert(String newnumber, String newshicode, String getNum) {
		int num = 0;
		int code = 0;
		boolean randNum = false;
		boolean randCode = false;
		
		Random rand = new Random();
		try {
			if(!newnumber.isEmpty()) num = Integer.parseInt(newnumber);
			else randNum = true;
			if(!newshicode.isEmpty()) code = Integer.parseInt(newshicode);
			else randCode = true;
			
			AppearDAO appearDAO = new AppearDAO();
			List<Integer> shiList = appearDAO.shiList();
			
			if(Integer.parseInt(getNum)>10) throw new NumberFormatException("最大10体までです");
			
			for(int i = 0 ; i < Integer.parseInt(getNum) ; i++) {
				if(randNum) num = rand.nextInt(386) + 1;
				if(randCode) {
					int index = new Random().nextInt(shiList.size());
					code = shiList.get(index);
				}
				
				if(!shiList.contains(code)) throw new NumberFormatException("市コードに含まれない数です");
				
				boolean success = appearDAO.insert(num, code);
				System.out.println(success);
			}
		} catch (NumberFormatException e) {
			System.out.println("不正な番号または市コードが入力されました" + e.getMessage());
		}
	}

	/** DAOを呼び出す */
	void delete(String deleteid) {
		try {
			int id = Integer.parseInt(deleteid);
			AppearDAO appearDAO = new AppearDAO();
			boolean success = appearDAO.delete(id);
			System.out.println(success);
		} catch (NumberFormatException e) {
			System.out.println("不正なIDが入力されました" + e.getMessage());
		}
	}
	
	//重複したIDのポケモンを削除
	List<Appear> checkDup(List<Appear> list) {
		
		for(int i = 0 ; i < list.size() ; i++) {
			Appear a = list.get(i);
			for(int j = i+1 ; j<list.size(); j++) {
				if(a.getId() == list.get(j).getId()) {
					a.mergeType(list.get(j).getType());
					list.remove(j);
					break;
				}
			}
			
		}
			
		return list; 
	}
	
	List<Appear> selectType(List<Appear> list, String select, String select2){
		List<Appear> l = new ArrayList<>();
		
		String s1 = "none";
		String s2 = "none";
		
		if(!select.equals("指定なし")) {
			s1 = select;	
			if(!select2.equals("指定なし")) s2 = select2;
		}
		else s1 = select2;
		
		for(Appear ap : list) {
			if(s2 != "none") {
				if(ap.getType().contains(s1) && ap.getType().contains(s2)) {
					l.add(ap);
				}
			} else {
				if(ap.getType().contains(s1)) {
					l.add(ap);
				}
			}
		}
		
		return l;
	}
}
