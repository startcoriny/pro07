package sec01.ex01;

import java.sql.Connection;
import java.sql.Date;
// A connection (session) with a specific database
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
//The object used for executing a static SQL statement and returning the results it produces. 

public class MemberDAO {

	//필
	private static final String driver = "oracle.jdbc.driver.OracleDriver";
	private static final String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	// jdbc : 사용할 JDBC 드라이버(oracle, mysql) :드라이버타입 :@서버이름(ip):포트번호:DB서비스아이디(SID)
	// :하나만 없어도 톰캣에는 에러가 없어도 서버에ㅔ서는 문제가 생김.
	private static final String user = "scott";
	private static final String pwd = "12341234";

//	Statement stmt; 
	// Statement클래스는 데이터베이스 연결로부터 SQL문을 수행할 수 있도록 해주는 클래스.
	PreparedStatement pstmt; 
	//PreparedStatement
	//SQL문을 미리 만들어 두고 변수를 따로 입력하는 방식.
	ResultSet rs;
	Connection conn;
	//메
	
	public List<MemberVO> listMembers(){
		
		List<MemberVO> list= new ArrayList<MemberVO>();
		
		connDB();
		
		
		
		String query = "select * from t_member ";
		//쿼리라는 참조 변수안에 select문 넣기. //like '홍'은 홍이 들어간 id만 찾아라.
		System.out.println(query);
		
		
		try {
//			rs = pstmt.executeQuery(query);
			pstmt = conn.prepareStatement(query);
			
			
			rs = pstmt.executeQuery();
			//Select문을 수행할때 사용함. 반환값은 ResultSet클래스의 인스턴스로,
			//해당 Select문의 결과에 해당하는 데이터에 접근할 수 있는 방법을 제공.
			
			// ResultSet은 객체이므로 다시 풀어주는 과정을 코딩
			// ResultSet은 데이터베이스 내부적으로 수행된 SQL문의 처리 결과를 JDBC에서 쉽게 관리할수 있도록 해줌.
			// ResultSet은 next()메서드를 이용해서 다음 로우(row, 행)으로 이동할수 있다.
			// 커서를 최초 데이터 위치로 이동시키려면, ResultSet을 사용하기 전에 rs.next()메소드를 한 번 호출해줄 필요가 있다.
			// 대부분의 경우 executeQuery()메소드를 수행한 후 while(rs.next())와 같이 더 이상 로우가 없을 때까지 루프를 돌면서 데이터를 처리하는 방법을 이용함.
			
			
			//rs.next() 한줄씩 커서(결과가 여러줄이므로 맨 위에 줄부터 접근하게 하는 포인터)를 옮김.
			while(rs.next()) {
				
				//getString에서 숫자로 쓰려면 칼럼인덱스를 선택해서 숫자로 사용하면 칼럼 위치에따라서 
				//0부터 시작하는것이아닌 1부터 순서대로 시작하면 위치에 맞는 칼럼을 참조.
				String id = rs.getString("id"); // 칼럼(sql의 테이블 제목?)이름(ID)에 해당하는 값을 가져옴.
				String pwd = rs.getString("pwd");
				String name = rs.getString("name");
				String email = rs.getString("email");
				Date joinDate = rs.getDate("joinDate"); // 날짜 는 getDate로 받음.
				System.out.println(id+pwd+name+email+joinDate);
				
				
				MemberVO vo = new MemberVO();
				vo.setId(id);
				vo.setPwd(pwd);
				vo.setName(name);
				vo.setEmail(email);
				vo.setJoinDate(joinDate);
				//위의 값들중 하나라도 세팅을 안하면 값들의 null 값으로 변한다.
				
				list.add(vo);
				System.out.println("============================");
			}
			
			rs.close(); // ResultSet의 사용이 끝났으면 rs.close()메소드를 이요해 ResultSet을 닫아주도록함.
			pstmt.close();
			conn.close(); // 데이터 베이스와의 연결을 관리하는 Connection인스턴트는 사용한 뒤 반납하지 않으면 계속해서 연결을 유지하므로, 어느 시점에서는
			// 데이터 베이스 연결이 부족한 상황이 발생할 수 있다. 따라서 사용자가 많은 시스템일수록 커넥션 관리가 중요한데, 이를 위해 커넥션 풀(connection pool)을 이용함.
			// 커넥션 풀의 사용여부와는 상관없이 데이터베이스 연결은 해당 연결을 통한 작업이 모두 끝나는 시점에서 close()메서드를 이용해 해제하는것이 좋다.
			
			
		} catch (Exception e) {
			System.out.println("sql 문장을 돌리는데 문제가 생김.");
//			e.printStackTrace();
		}
		
		
		
		return list;
	}
	
	
	private void connDB() {
		try {
			Class.forName(driver);
			// 해당 드라이버 클래스를 불러옴.
			
			System.out.println("oracle 드라이버  로딩 성공.");
			conn = DriverManager.getConnection(url, user, pwd);
			
			
			System.out.println("Connection 생성 성공");
//			pstmt= conn.createStatement();//Creates a Statement object for sendingSQL statements to the database.
			
			System.out.println("Statement 생성 성공");

			//여기서부터는 요령
		}catch (Exception e) {
			System.out.println("DB 연결에 문제가 생김");
//			e.printStackTrace();
			
			
		}
	}
	
}
