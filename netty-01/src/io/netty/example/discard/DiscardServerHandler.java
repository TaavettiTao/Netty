package io.netty.example.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * netty服务端handler
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter { // (1)

    @Override
    public void channelRead(final ChannelHandlerContext ctx, Object msg) { // (2)
    	//读取客户端数据
    	ByteBuf buf=(ByteBuf)msg;
        int bytes=buf.readableBytes();
        System.out.println("可读字节数："+bytes);
        byte[] data=new byte[bytes];
        buf.readBytes(data);
        System.out.println("读取数据："+new String(data));
        
        //服务端在接收到客户端数据处理之后，给客户端返回响应，之后关闭通道连接。
        final ByteBuf time = ctx.alloc().buffer(4); // (2)
        time.writeBytes("server receives data!".getBytes());
        final ChannelFuture f = ctx.writeAndFlush(time); // (3)
        f.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                assert f == future;
                ctx.close();
            }
        });
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