package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ShohinDAO;
import model.data.ShohinRecord;

/**
 * 商品検索画面のコントローラ
 */
@WebServlet("/ShohinSearch")
public class ShohinSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Getリクエストを処理します。
	 * ①ブラウザからURLを入力され、index.jspから転送されてきた場合。
	 * ②別画面から「戻る」リンクで戻ってきた場合
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 商品検索画面へフォワード
		RequestDispatcher dispatcher =
				request.getRequestDispatcher("/WEB-INF/jsp/shohinSearch.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * Postリクエストを処理します。
	 * ①「検索」ボタンを押された時の処理
	 * ②「削除」ボタンを押された時の処理
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String message = "";	// レスポンス後に画面に表示する結果メッセージ

		// リクエストパラメータより処理種別を取得
		request.setCharacterEncoding("UTF-8");	// この命令は初回のgetParameterメソッド呼び出しより前にしておく必要がある
		String action = request.getParameter("action");

		// 処理種別により処理を分岐
		if("search".equals(action)) {
			// 処理種別がsearch（検索）の場合、検索処理を実施

			// リクエストパラメータから検索条件の取得
			String sSyohinMei = request.getParameter("s_syohinmei");
			String sBunrui = request.getParameter("s_bunrui");

			// 検索条件に該当する商品情報を商品テーブルから検索
			ShohinDAO shohinDAO = new ShohinDAO();
			List<ShohinRecord> shohinList = shohinDAO.select(sSyohinMei, sBunrui);

			// 検索結果をリクエストスコープに保存
			request.setAttribute("shohinList", shohinList);

		} else if("delete".equals(action)) {
			// 処理種別がdelete（削除）の場合、削除処理を実施
			String id = request.getParameter("select");

			// 検索条件に該当する商品情報を商品テーブルから検索
			ShohinDAO shohinDAO = new ShohinDAO();
			int delCnt = shohinDAO.delete(id);

			// 削除結果から結果メッセージを設定
			if(delCnt == 1) {
				message = "削除が完了しました";
			}
		}

		// 結果メッセージをリクエストスコープに保存
		request.setAttribute("message", message);

		// 商品検索画面（自画面）へフォワード
		RequestDispatcher dispatcher =
				request.getRequestDispatcher("/WEB-INF/jsp/shohinSearch.jsp");
		dispatcher.forward(request, response);
	}

}
