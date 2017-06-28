<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateWalletTransactionsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('wallet_transactions', function (Blueprint $table) {
            $table->increments('id');
            $table->integer('userId')->unsigned();
            $table->integer('walletId')->unsigned();
            $table->integer('trcType');
            $table->dateTime('trcTime');
            $table->string('walletCode');
            $table->timestamps();

            $table->foreign('userId')
                  ->references('id')->on('users')
                  ->onDelete('cascade');
            $table->foreign('walletId')
                  ->references('id')->on('wallets')
                  ->onDelete('cascade');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('wallet_transactions');
    }
}
