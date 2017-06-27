<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateUserAdditionalInfosTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('user_additional_infos', function (Blueprint $table) {
            $table->integer('userId');
            $table->integer('infoId');
            $table->timestamps();

            $table->foreign('userId')->references('id')->on('users');
            $table->foreign('infoId')->references('id')->on('additional_infos');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('user_additional_infos');
    }
}
