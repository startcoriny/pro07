package sec02.ex01;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDAO {
	private Connection con;
	private PreparedStatement pstmt;
	private DataSource dataFactory; // 변수 선언.

	public MemberDAO() {
		// JNDI방식의 연결로 MamberDAO객체를 초기화.
		// JNDI(Java Naming and Directory Interface)는 디렉터리 서비스에서 제공하는
		// 데이터 및 객체를 발견(discover)하고 참고(lookup)하기 위한 자바 API이다.
		// JNDI의 용도.
		
		// * 자바 애플리케이션을 외부 디렉터리 서비스에 연결( EX) 주소 데이터베이스 또는 LDAP서버)
		// * 자바 애플릿이 호스팅 웹 컨테이너가 제공하는 구성 정보를 참고.
		
		// * 외부의  있는 객체를 가져오기 위한기술.
		// * Tomcat과 같은 WAS를 보면 특정 폴더에 필요한 데이터 소스(라이브러리)가 있는데 그것을
		//   우리가 사용하기 위해 JNDI를 이용해서 가져오는것.
		
		// 연결하고 싶은 데이터베이스의 DB Pool을 미리 Naming시켜주는 방법중 하나.
		// 저장해놓은 WAS의 데이터베이스 정보에 JNDI를 설정해 놓으면 웹 애플리 케이션은 JNDI만 호출하면 간단.
		// 운영,관리,최적화 문제 대처에 다양한 이점이 있기 때문에 JNDI를 사용.
		
		// 사용 하려면 web.xml과 server.xml 파일을 설정해 줘야 한다.
		
		try {
			// JNDI부터 DataSource얻기.
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			//JNDI에 접근하기위해 기본 경로(java:/comp/env)를 지정. 
			
			// InitialContext()는 웹 어플리 케이션이 처음으로 배치될 때 설정.
			// 모든 설정된 엔트리와 자원은 JNDI namespace의 java:/comp/env 부분에 놓이게 됌.
			// 그래서 접근을할때 위 코드처럼 해준다. - 실제 DB와 연결하는 코드.
			
			dataFactory = (DataSource) envContext.lookup("jdbc/oracle");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<MemberVO> listMembers() {
		List<MemberVO> list = new ArrayList<MemberVO>();
		try {
			// connDB();
			con = dataFactory.getConnection();
			String query = "select * from t_member ";
			System.out.println("prepareStatememt: " + query);
			pstmt = con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String id = rs.getString("id");
				String pwd = rs.getString("pwd");
				String name = rs.getString("name");
				String email = rs.getString("email");
				Date joinDate = rs.getDate("joinDate");
				MemberVO vo = new MemberVO();
				vo.setId(id);
				vo.setPwd(pwd);
				vo.setName(name);
				vo.setEmail(email);
				vo.setJoinDate(joinDate);
				list.add(vo);
			}
			rs.close();
			pstmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public void addMember(MemberVO memberVO) {
		try {
			con = dataFactory.getConnection();
			String id = memberVO.getId();
			String pwd = memberVO.getPwd();
			String name = memberVO.getName();
			String email = memberVO.getEmail();
			String query = "insert into t_member";
			query += "(id,pwd,name,email)";
			query += " values(?,?,?,?)";
			System.out.println("prepareStatememt: " + query);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			pstmt.setString(3, name);
			pstmt.setString(4, email);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void delMember(String id) {
		try {
			con = dataFactory.getConnection();
			String query = "delete from t_member" + " where id=?";
			System.out.println("prepareStatememt:" + query);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}