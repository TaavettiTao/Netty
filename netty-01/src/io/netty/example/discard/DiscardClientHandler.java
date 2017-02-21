package io.netty.example.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class DiscardClientHandler extends ChannelInboundHandlerAdapter {
    
    //接收服务端消息
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
		ByteBuf buffer=(ByteBuf)msg;
		int len=buffer.readableBytes();
		byte[] bytes=new byte[len];
		buffer.readBytes(bytes);
		System.out.println("服务端响应数据："+new String(bytes));
    	
    }
    
    

    //连接成功，向服务端发送消息
    @Override
	public void channelActive(final ChannelHandlerContext ctx) throws Exception {
    	final ByteBuf time = ctx.alloc().buffer(4); // (2)
        time.writeBytes("sssss".getBytes());
        final ChannelFuture f = ctx.writeAndFlush(time); // (3)
        
       /* 如果加上以下代码，客户端在连接服务器端发送信息之后，即会把连接断掉，此时将无法接收服务端响应，若希望接收响应，则不添加此代码，连接的关闭交由服务端做即可。
        * f.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                assert f == future;
                ctx.close();
            }
        });*/
	}



	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
    
    
    
}
