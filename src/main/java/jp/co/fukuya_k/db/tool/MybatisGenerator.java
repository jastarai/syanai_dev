package jp.co.fukuya_k.db.tool;

import java.util.List;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.TopLevelClass;

public class MybatisGenerator extends PluginAdapter {

	@Override
	public boolean validate(List<String> arg0) {
		return true;
	}

	@Override
	public void initialized(IntrospectedTable introspectedTable) {

		// クラス名変更
/*
		String javaMapperType = introspectedTable.getMyBatis3JavaMapperType();
		introspectedTable.setMyBatis3JavaMapperType(javaMapperType.replaceAll("Mapper$","Dao"));
*/

		String exampleType = introspectedTable.getExampleType();
		introspectedTable.setExampleType(exampleType.replaceAll("Example$", "Helper"));
		// メソッド名変更
		introspectedTable.setCountByExampleStatementId(introspectedTable.getCountByExampleStatementId().replaceAll("ByExample", "ByHelper"));
		introspectedTable.setDeleteByExampleStatementId(introspectedTable.getDeleteByExampleStatementId().replaceAll("ByExample", "ByHelper"));
		introspectedTable.setSelectByExampleStatementId(introspectedTable.getSelectByExampleStatementId().replaceAll("ByExample", "ByHelper"));
		introspectedTable.setExampleWhereClauseId(introspectedTable.getExampleWhereClauseId().replaceAll("^Example", "Helper"));
		introspectedTable.setMyBatis3UpdateByExampleWhereClauseId(introspectedTable.getMyBatis3UpdateByExampleWhereClauseId().replaceAll("ByExample", "ByHelper"));
		introspectedTable.setSelectByExampleWithBLOBsStatementId(introspectedTable.getSelectByExampleWithBLOBsStatementId().replaceAll("ByExample", "ByHelper"));
		introspectedTable.setUpdateByExampleSelectiveStatementId(introspectedTable.getUpdateByExampleSelectiveStatementId().replaceAll("ByExample", "ByHelper"));
		introspectedTable.setUpdateByExampleStatementId(introspectedTable.getUpdateByExampleStatementId().replaceAll("ByExample", "ByHelper"));
		introspectedTable.setUpdateByExampleWithBLOBsStatementId(introspectedTable.getUpdateByExampleWithBLOBsStatementId().replaceAll("ByExample", "ByHelper"));
	}

	// 共通インターフェイス
	private FullyQualifiedJavaType commonEntityInterface = new FullyQualifiedJavaType("jp.co.fukuya_k.system.interfaces.FukuyaEntityInterface");

	@Override
	public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable table) {
		extModel(topLevelClass, table);
		return true;
	}
//	@Override
//	public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass, IntrospectedTable table) {
//		implement(topLevelClass, table);
//		return true;
//	}
	@Override
	public boolean modelRecordWithBLOBsClassGenerated(TopLevelClass topLevelClass, IntrospectedTable table) {
		extModel(topLevelClass, table);
		return true;
	}

	/*
	 * モデル拡張
	 * ・共通インターフェイスを継承
	 * ・forupdate判定フラグ追加
	 */
	protected void extModel(TopLevelClass topLevelClass, IntrospectedTable table) {
		topLevelClass.addImportedType(commonEntityInterface);
		topLevelClass.addSuperInterface(commonEntityInterface);

//		if (null == topLevelClass.getSuperClass()) {
//			topLevelClass.addImportedType(commonEntitylClass);
//			topLevelClass.setSuperClass(commonEntitylClass);
//		}

//		Field field = new Field("forUpdate", new FullyQualifiedJavaType("java.lang.boolean"));
//		field.setVisibility(JavaVisibility.PRIVATE);
//		field.setInitializationString("false");
//		context.getCommentGenerator().addFieldComment(field, table);
//		topLevelClass.addField(field);
//
//		Method set = new Method("setForUpdate");
//		set.setVisibility(JavaVisibility.PUBLIC);
//		set.addParameter(new Parameter(new FullyQualifiedJavaType("java.lang.boolean"), "forUpdate"));
//		set.addBodyLine("this.forUpdate = forUpdate;");
//		context.getCommentGenerator().addGeneralMethodComment(set, table);
//		topLevelClass.addMethod(set);
//
//		Method get = new Method("getForUpdate");
//		get.setVisibility(JavaVisibility.PUBLIC);
//		get.setReturnType(new FullyQualifiedJavaType("java.lang.boolean"));
//		get.addBodyLine("return this.forUpdate;");
//		context.getCommentGenerator().addGeneralMethodComment(get, table);
//		topLevelClass.addMethod(get);
	}

//	@Override
//	public boolean sqlMapSelectByPrimaryKeyElementGenerated(XmlElement xmlElement, IntrospectedTable table) {
//		extSqlMapper(xmlElement);
//		return true;
//	}
//	
//	/*
//	 * SqlMapper拡張
//	 * ・forupdateノードの追加
//	 * （selectByPrimaryKeyを対象）
//	 */
//	protected void extSqlMapper(XmlElement xmlElement) {
//		XmlElement element = new XmlElement("if");
//		TextElement textElement = new TextElement("for update ");
////		Attribute attribute = new Attribute("test", "forUpdate == true");
//		Attribute attribute = new Attribute("test", "null != updDt");
//		element.addAttribute(attribute);
//		element.addElement(textElement);
//		xmlElement.addElement(element);
//	}
}
