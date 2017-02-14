package io.netty.example.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * netty服务端handler
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter { // (1)

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
    	//读取客户端数据
    	ByteBuf buf=(ByteBuf)msg;
        int bytes=buf.readableBytes();
        System.out.println("可读字节数："+bytes);
        byte[] data=new byte[bytes];
        buf.readBytes(data);
        System.out.println("读取数据："+new String(data));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        cause.printStackTrace();
        ctx.close();
    }
    
    @Override
    public void channelActive(final ChannelHandlerContext ctx) { // (1)
    }

    
    
    
}