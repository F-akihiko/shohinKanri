<%@page import="model.data.ShohinRecord"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
List<ShohinRecord> shohinList = (List<ShohinRecord>)request.getAttribute("shohinList");

//結果メッセージをリクエストスコープから取得
String message = (String)request.getAttribute("message");
if(message == null) {
	message = "";
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品検索</title>
</head>
<body>
<h1>商品検索</h1>
<%= message %>
<form action="/shohinKanri/ShohinSearch?action=search" method="post">
<table border="1">
	<tr>
		<th>商品名</th>
		<th><input type="text" name="s_syohinmei"></th>
		<th>商品分類</th>
		<th>
			<select name="s_bunrui">
				<option value=""></option>
				<option value="衣服">衣服</option>
				<option value="キッチン用品">キッチン用品</option>
				<option value="事務用品">事務用品</option>
			</select>
		</th>
	</tr>
</table>
<input type="submit" value="検索">
</form>
<br>
<br>
<form action="/shohinKanri/ShohinSearch?action=delete" method="post">
<table border="1">
	<tr>
		<th>選択</th>
		<th>商品ID</th>
		<th>商品名</th>
		<th>商品分類</th>
		<th>販売単価</th>
		<th>仕入単価</th>
	</tr>
<% if(shohinList != null) {
	for(ShohinRecord shohin : shohinList) { %>
	<tr>
		<td><input type="radio" name="select" value="<%= shohin.getShohinId() %>"></td>
		<td><%= shohin.getShohinId() %></td>
		<td><%= shohin.getShohinMei() %></td>
		<td><%= shohin.getShohinBunrui() %></td>
		<td><%= shohin.getHanbaiTanka() %></td>
		<td><%= shohin.getShiireTanka() %></td>
	</tr>
<%  }
}%>
</table>
<a href="/shohinKanri/ShohinAdd">追加</a>
<input type="submit" value="削除">
</form>
<br>
</body>
</html>