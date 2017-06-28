<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateUserWorkTimesTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('user_work_times', function (Blueprint $table) {
            $table->integer('userId')->unsigned();
            $table->integer('workTimeId')->unsigned();
            $table->decimal('cost');
            $table->timestamps();

            $table->foreign('userId')
                  ->references('id')->on('users')
                  ->onDelete('cascade');
            $table->foreign('workTimeId')
                  ->references('id')->on('work_times')
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
        Schema::dropIfExists('user_work_times');
    }
}
