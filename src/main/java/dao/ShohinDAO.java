package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.data.ShohinRecord;

/**
 * 「商品」テーブルへのアクセスを担当するDAOクラスです。
 */
public class ShohinDAO{
    private final String url = "jdbc:postgresql://localhost:5432/shop";
    private final String user = "postgres";
    private final String password = "root";

	/**
	 * 「商品」テーブルから商品名と商品分類でデータを検索し、検索結果を返します。
	 */
    public List<ShohinRecord> select(String shohinMei, String shohinBunrui) {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        List<ShohinRecord> sRecords = null;

		try {
	         /* JDBCドライバの定義 */
	         Class.forName("org.postgresql.Driver");

	         /* PostgreSQLへの接続 */
	         con = DriverManager.getConnection(url, user, password);

	         /* SELECT文の準備 */
	         String sql = "SELECT * ";
	         sql += "FROM Shohin ";
	         sql += "WHERE shohin_mei like ? ";
	         sql += "AND shohin_bunrui like ? ";
	         sql += "ORDER BY shohin_id; ";
	         st = con.prepareStatement(sql);
	         st.setString(1, "%" + shohinMei + "%");
	         st.setString(2, "%" + shohinBunrui + "%");

	         /* SELECT文の実行 */
	         rs = st.executeQuery();

	         /* 結果をリストに移し替える */
	         sRecords = makeResultData(rs);

		} catch(Exception e) {
			System.out.println("DBアクセス時にエラーが発生しました。");
			e.printStackTrace();
		} finally {
	         /* PostgreSQLとの接続を切断 */
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {}
			}

			if(st != null) {
				try {
					st.close();
				} catch (SQLException e) {}
			}

			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {}
			}
		}

        return sRecords;
    }

    /**
     * 「商品」テーブルに1件データを登録します。
     */
    public int insert(String shohinId,
    					String shohinMei,
    					String shohinBunrui,
    					String hanbaiTanka,
    					String shiireTanka) {
        Connection con = null;
        PreparedStatement st = null;
        int insCnt = 0;		// 更新件数

		try {
	         /* JDBCドライバの定義 */
	         Class.forName("org.postgresql.Driver");

	         /* PostgreSQLへの接続 */
	         con = DriverManager.getConnection(url, user, password);

	         /* INSERT文の準備 */
	         String sql = "";
	         sql = "INSERT INTO shohin(shohin_id, shohin_mei, shohin_bunrui, hanbai_tanka, shiire_tanka, torokubi) ";
	         sql += "VALUES(?, ?, ?, ?, ?, NULL);";

	         st = con.prepareStatement(sql);
	         st.setString(1, shohinId);
	         st.setString(2, shohinMei);
	         st.setString(3, shohinBunrui);
	         if("".equals(hanbaiTanka)) {
		         st.setInt(4, 0);
	         } else {
		         st.setInt(4, Integer.parseInt(hanbaiTanka));
	         }
	         if("".equals(shiireTanka)) {
		         st.setInt(5, 0);
	         } else {
		         st.setInt(5, Integer.parseInt(shiireTanka));
	         }

	         /* INSERT文の実行 */
	         insCnt = st.executeUpdate();

		} catch(Exception e) {
			System.out.println("DBアクセス時にエラーが発生しました。");
			e.printStackTrace();
		} finally {
	        /* PostgreSQLとの接続を切断 */
			if(st != null) {
				try {
					st.close();
				} catch (SQLException e) {}
			}

			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {}
			}
		}

        return insCnt;
    }

    /**
     * 「商品」テーブルから商品IDでデータを削除します。
     */
    public int delete(String id) {
        /* PostgreSQLへの接続情報 */
        Connection con = null;
        PreparedStatement st = null;
        int delCnt = 0;		// 削除件数

		try {
	         /* JDBCドライバの定義 */
	         Class.forName("org.postgresql.Driver");

	         /* PostgreSQLへの接続 */
	         con = DriverManager.getConnection(url, user, password);

	         /* SELECT文の準備 */
	         String sql = "";
	         sql = "DELETE FROM Shohin ";
	         sql += "Where shohin_id = ?;";

	         st = con.prepareStatement(sql);
	         st.setString(1, id);

	         /* DELETE文の実行 */
	         delCnt = st.executeUpdate();

		} catch(Exception e) {
			System.out.println("DBアクセス時にエラーが発生しました。");
			e.printStackTrace();
		} finally {
	         /* PostgreSQLとの接続を切断 */
			if(st != null) {
				try {
					st.close();
				} catch (SQLException e) {}
			}

			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {}
			}
		}

		return delCnt;
    }

    /**
     * 検索結果をリストで返します。
     */
    public ArrayList<ShohinRecord> makeResultData(ResultSet rs) throws Exception {

    	// 全検索結果を格納するリストを準備
    	ArrayList<ShohinRecord> sRecords = new ArrayList<ShohinRecord>();

    	while(rs.next()) {
    		// 1行分のデータを読込む
    		String shohinId = rs.getString("shohin_id");
    		String shohinMei = rs.getString("shohin_mei");
    		String shohinBunrui = rs.getString("shohin_bunrui");
    		int hanbaiTanka = rs.getInt("hanbai_tanka");
    		int shiireTanka = rs.getInt("shiire_tanka");
    		String torokubi = rs.getString("torokubi");

    		// 1行分のデータを格納するインスタンス
    		ShohinRecord sRecord = new ShohinRecord(shohinId,
    												shohinMei,
    												shohinBunrui,
    												hanbaiTanka,
    												shiireTanka,
    												torokubi);

            // リストに1行分のデータを追加する
            sRecords.add(sRecord);
    	}
    	return sRecords;
    }
}