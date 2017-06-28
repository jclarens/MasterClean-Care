<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateUsersTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('users', function (Blueprint $table) {
            $table->increments('id');
            $table->string('name');
            $table->string('email')->unique();
            $table->string('password');
            $table->tinyInteger('gender');
            $table->string('bornPlace');
            $table->date('bornDate');
            $table->string('phone');
            $table->integer('province')->unsigned();
            $table->integer('city')->unsigned();
            $table->string('address')->nullable();
            $table->string('location');
            $table->integer('religion');
            $table->integer('race');
            $table->tinyInteger('userType');
            $table->string('profileImgName');
            $table->string('profileImgPath');
            $table->tinyInteger('status');
            $table->rememberToken();
            $table->timestamps();

            $table->foreign('province')
                  ->references('id')->on('places');
            $table->foreign('city')
                  ->references('id')->on('places');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('users');
    }
}
