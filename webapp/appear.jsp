<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="db.Appear, java.util.List, db.AppearServlet" %>
<%
List<Appear> appearList = (List<Appear>) request.getAttribute("list");
%>
<%
AppearServlet aps = new AppearServlet();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>
		<a href="AppearServlet">ポケモン出現DB</a>
	</h1>
	<hr>
	<form action="AppearServlet" method="POST">
		<input type="radio" name="item" value="ID" checked="checked">ID
		<input type="radio" name="item" value="番号">番号 
		<input type="radio" name="item" value="名前">名前 <br> 

			<input type="radio" name="order" value="asc" checked="checked">昇順 
			<input type="radio" name="order" value="desc">降順 <br> 

				タイプ1：
				<select name = "select">
					<option value="指定なし">指定なし</option>
					<option value="あく">あく</option>
					<option value="いわ">いわ</option>
					<option value="かくとう">かくとう</option>
					<option value="くさ">くさ</option>
					<option value="こおり">こおり</option>
					<option value="じめん">じめん</option>
					<option value="でんき">でんき</option>
					<option value="どく">どく</option>
					<option value="はがね">はがね</option>
					<option value="ひこう">ひこう</option>
					<option value="ほのお">ほのお</option>
					<option value="みず">みず</option>
					<option value="むし">むし</option>
					<option value="エスパー">エスパー</option>
					<option value="ゴースト">ゴースト</option>
					<option value="ドラゴン">ドラゴン</option>
					<option value="ノーマル">ノーマル</option>
					<option value="フェアリー">フェアリー</option>
				</select>

			タイプ2：
				<select name = "select2">
					<option value="指定なし">指定なし</option>
					<option value="あく">あく</option>
					<option value="いわ">いわ</option>
					<option value="かくとう">かくとう</option>
					<option value="くさ">くさ</option>
					<option value="こおり">こおり</option>
					<option value="じめん">じめん</option>
					<option value="でんき">でんき</option>
					<option value="どく">どく</option>
					<option value="はがね">はがね</option>
					<option value="ひこう">ひこう</option>
					<option value="ほのお">ほのお</option>
					<option value="みず">みず</option>
					<option value="むし">むし</option>
					<option value="エスパー">エスパー</option>
					<option value="ゴースト">ゴースト</option>
					<option value="ドラゴン">ドラゴン</option>
					<option value="ノーマル">ノーマル</option>
					<option value="フェアリー">フェアリー</option>
				</select><br>

			<input type="submit" name="submit" value="並び替え">

		<hr>
		番号<input type="text" name="newnumber"> 
		市コード<input type="text" name="newshicode"> <input type="submit" name="submit" value="登録">
		<br>
		出現数(10以下)<input type="text" name="getNum"> <input type="submit" name="submit" value="出現させる">
		<br>※番号と市コードのいずれかを指定すれば、そのポケモンを、もしくは場所でランダムに出現する
		<hr>
		ID<input type="text" name="deleteid"> <input type="submit"
			name="submit" value="削除">
	</form>
	<hr>
	<a href="AppearServlet?shimei=習志野市">習志野市</a>
	<a href="AppearServlet?shimei=船橋市">船橋市</a>
	<hr>

	<%
	if (appearList != null) {
	%>
	出現情報
	<table border="1">
		<tr>
			<th>ID</th>
			<th>番号</th>
			<th>名前</th>
			<th>すがた</th>
			<th>タイプ</th>
			<th>県名</th>
			<th>市名</th>
			<th>日付</th>
			<th>時刻</th>
		</tr>
		<%
		for (Appear appear : appearList) {
		%>
		<tr>
			<td><%=appear.getId()%></td>
			<td><%=appear.getNumber()%></td>
			<td><%=appear.getName()%></td>
			<td><img src="https://img.yakkun.com/poke/icon96/n<%=appear.getNumber()%>.gif"</td>
			<td><%=appear.getType()%></td>
			<td><%=appear.getKen()%></td>
			<td><%=appear.getShi()%></td>
			<td><%=appear.getDate()%></td>
			<td><%=appear.getTime()%></td>
			
		</tr>
		<%
		}
		%>
	</table>
	<%
	}
	%>
</body>
</html>
