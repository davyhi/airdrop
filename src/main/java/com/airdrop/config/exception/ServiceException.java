package com.airdrop.config.exception;

/**   
* @Description: 业务逻辑自定义异常类(继承运行时异常)
* @author ShengGuang.Ye
* @date 2018/7/11 10:21
* @version V1.0   
*/
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 错误编码
     */
    private int errorCode;

    /**
     * 消息是否为属性文件中的Key
     */
    private boolean propertiesKey = true;

    /**
     * 构造一个基本异常.
     *
     * @param message
     *            信息描述
     */
    public ServiceException(String message)
    {
        super(message);
    }

    /**
     * 构造一个基本异常.
     *
     * @param errorCode
     *            错误编码
     * @param message
     *            信息描述
     */
    public ServiceException(int errorCode, String message)
    {
        this(errorCode, message, true);
    }

    /**
     * 构造一个基本异常.
     *
     * @param errorCode
     *            错误编码
     * @param message
     *            信息描述
     */
    public ServiceException(int errorCode, String message, Throwable cause)
    {
        this(errorCode, message, cause, true);
    }

    /**
     * 构造一个基本异常.
     *
     * @param errorCode
     *            错误编码
     * @param message
     *            信息描述
     * @param propertiesKey
     *            消息是否为属性文件中的Key
     */
    public ServiceException(int errorCode, String message, boolean propertiesKey)
    {
        super(message);
        this.setErrorCode(errorCode);
        this.setPropertiesKey(propertiesKey);
    }

    /**
     * 构造一个基本异常.
     *
     * @param errorCode
     *            错误编码
     * @param message
     *            信息描述
     */
    public ServiceException(int errorCode, String message, Throwable cause, boolean propertiesKey)
    {
        super(message, cause);
        this.setErrorCode(errorCode);
        this.setPropertiesKey(propertiesKey);
    }

    /**
     * 构造一个基本异常.
     *
     * @param message
     *            信息描述
     * @param cause
     *            根异常类（可以存入任何异常）
     */
    public ServiceException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public int getErrorCode()
    {
        return errorCode;
    }

    public void setErrorCode(int errorCode)
    {
        this.errorCode = errorCode;
    }

    public boolean isPropertiesKey()
    {
        return propertiesKey;
    }

    public void setPropertiesKey(boolean propertiesKey)
    {
        this.propertiesKey = propertiesKey;
    }

}
